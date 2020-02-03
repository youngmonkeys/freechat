package vn.team.freechat.plugin.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyServerEventHandler;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfoxserver.constant.EzyEventNames;
import com.tvd12.ezyfoxserver.constant.EzyLoginError;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;
import com.tvd12.ezyfoxserver.exception.EzyLoginErrorException;

import lombok.Getter;
import lombok.Setter;
import vn.team.freechat.common.data.ChatNewUser;
import vn.team.freechat.common.data.ChatUser;
import vn.team.freechat.common.service.ChatUserService;

@Getter
@Setter
@EzySingleton
@EzyServerEventHandler(EzyEventNames.USER_LOGIN)
public class ChatUserLoginController 
		extends EzyAbstractPluginEventController<EzyUserLoginEvent> {

	@EzyAutoBind
	private ChatUserService userService;
	
	@Override
	public void handle(EzyPluginContext ctx, EzyUserLoginEvent event) {
		logger.info("handle user {} login in", event.getUsername());

		validateEvent(event);
		
		String username = event.getUsername();
		String password = encodePassword(event.getPassword());
		
		ChatNewUser newUser = getUser(username, password);
		ChatUser userData = newUser.getUser();
		
		if(!newUser.isNewUser()) {
			if(!userData.getPassword().equals(password))
				throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
		}
		
		event.setStreamingEnable(true);
		event.setUserProperty("dataId", userData.getId());
		
		logger.info("username and password match, accept user: {}", event.getUsername());
	}

	private void validateEvent(EzyUserLoginEvent event) {
		String password = event.getPassword();
		if(EzyStrings.isNoContent(password))
			throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
		if(password.length() < 6)
			throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
		
	}
	
	private ChatNewUser getUser(String username, String password) {
		ChatNewUser newUser = userService.createUser(
				username, 
				user -> {
					user.setPassword(password);
					user.setOnline(true);
				}
		);
		return newUser;
	}
	
	private String encodePassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encoded = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			return new String(encoded, StandardCharsets.UTF_8);
		}
		catch (Exception e) {
			throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
		}
	}
}