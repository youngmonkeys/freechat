package com.tvd12.freechat.plugin.controller;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.security.EzySHA256;
import com.tvd12.ezyfoxserver.constant.EzyEventNames;
import com.tvd12.ezyfoxserver.constant.EzyLoginError;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;
import com.tvd12.ezyfoxserver.exception.EzyLoginErrorException;
import com.tvd12.freechat.common.entity.ChatUser;
import com.tvd12.freechat.common.service.ChatUserService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EzySingleton
@EzyEventHandler(EzyEventNames.USER_LOGIN)
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

        ChatUser user = userService.getUser(username);
        if (user == null) {
            user = userService.createUser(username, password);
        }

        if (!user.getPassword().equals(password)) {
            throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
        }

        user.setOnline(true);
        userService.saveUser(user);

        event.setUserProperty("dataId", user.getId());

        logger.info("username and password match, accept user: {}", event.getUsername());
    }

    private void validateEvent(EzyUserLoginEvent event) {
        String password = event.getPassword();
        if (EzyStrings.isNoContent(password)) {
            throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
        }
        if (password.length() < 6) {
            throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
        }
    }

    private String encodePassword(String password) {
        return EzySHA256.cryptUtfToLowercase(password);
    }
}