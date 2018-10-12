package vn.team.freechat.plugin.controller;

import org.apache.commons.lang3.StringUtils;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyServerEventHandler;
import com.tvd12.ezyfoxserver.constant.EzyEventNames;
import com.tvd12.ezyfoxserver.constant.EzyLoginError;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;
import com.tvd12.ezyfoxserver.exception.EzyLoginErrorException;

import lombok.Getter;
import lombok.Setter;
import vn.team.freechat.common.data.ChatNewUser;
import vn.team.freechat.common.service.ChatUserService;
import vn.team.freechat.plugin.constant.ChatLoginError;

@Getter
@Setter
@EzySingleton
@EzyServerEventHandler(EzyEventNames.USER_LOGIN)
public class ChatUserLoginController extends EzyAbstractPluginEventController<EzyUserLoginEvent> {

	@EzyAutoBind
	private ChatUserService userService;
	
	@Override
	public void handle(EzyPluginContext ctx, EzyUserLoginEvent event) {
		getLogger().info("handle user {} login in vn-freechatvn", event.getUsername());
		checkPassword(event.getPassword());
		checkUser(event.getUsername(), event.getPassword());
		getLogger().info("username and password match, accept user: {}", event.getUsername());
	}

	private void checkPassword(String password) {
		if(StringUtils.isEmpty(password))
			throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
	}
	
	private void checkUser(String username, String password) {
		ChatNewUser newUser = userService.createUser(
				username, 
				user -> {
					user.setPassword(password);
					user.setOnline(true);
				}
		);
		if(newUser.isNewUser()) {
			if(!newUser.getUser().getPassword().equals(password))
				throw new EzyLoginErrorException(ChatLoginError.ALREADY_REGISTER);
		}
	}
}