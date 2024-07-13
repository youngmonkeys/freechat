package com.tvd12.freechat.plugin.controller;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfox.security.BCrypt;
import com.tvd12.ezyfox.security.EzySHA256;
import com.tvd12.ezyfoxserver.constant.EzyEventNames;
import com.tvd12.ezyfoxserver.constant.EzyLoginError;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;
import com.tvd12.ezyfoxserver.exception.EzyLoginErrorException;
import com.tvd12.freechat.common.model.ChatUserModel;
import com.tvd12.freechat.common.service.ChatUserService;
import lombok.AllArgsConstructor;

import static com.tvd12.ezyfox.io.EzyStrings.isBlank;
import static com.tvd12.freechat.common.constant.ChatConstants.*;

@EzySingleton
@AllArgsConstructor
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
        String password = event.getPassword();
        long userId = 0;
        ChatUserModel user = userService.getUserByUsername(username);
        if (user == null) {
            String hashedPassword = BCrypt.hashpw(
                password,
                HASH_PASSWORD_SALT
            );
            userId = userService.createUser(username, hashedPassword);
        } else {
            validateUserPassword(password, user.getPassword());
            userId = user.getId();
        }
        event.setUserProperty(PROPERTY_DATA_ID, userId);
        logger.info(
            "username and password match, accept user: {}",
            event.getUsername()
        );
    }

    private void validateEvent(EzyUserLoginEvent event) {
        String password = event.getPassword();
        if (isBlank(password)) {
            throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
        }
    }

    private void validateUserPassword(
        String requestPassword,
        String storeHashedPassword
    ) {
        String hashedPassword = EzySHA256.cryptUtfToLowercase(
            requestPassword
        );
        if (!hashedPassword.equals(storeHashedPassword)) {
            boolean valid = false;
            try {
                valid = BCrypt.checkpw(
                    requestPassword,
                    storeHashedPassword
                );
            } catch (Exception e) {
                logger.info("check password error: {}", e.getMessage());
            }
            if (!valid) {
                throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
            }
        }
    }
}
