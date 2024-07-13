package com.tvd12.freechat.app.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfox.security.BCrypt;
import com.tvd12.ezyfox.security.EzySHA256;
import com.tvd12.freechat.app.constant.ChatErrors;
import com.tvd12.freechat.common.model.ChatUserModel;
import com.tvd12.freechat.common.service.ChatUserService;
import lombok.Setter;

import static com.tvd12.freechat.app.constant.ChatCommands.UPDATE_PASSWORD;
import static com.tvd12.freechat.common.constant.ChatConstants.HASH_PASSWORD_SALT;


@Setter
@EzyPrototype
@EzyObjectBinding
@EzyRequestListener(UPDATE_PASSWORD)
public class ChatUpdatePasswordHandler extends ChatClientRequestHandler implements EzyDataBinding {

    // EzyValue: get value from object with key "oldPassword"
    // E.g.: request = [APP_REQUEST, [APP_ID, {"oldPassword": "test1234"}]]]
    private String oldPassword;
    private String newPassword;

    @EzyAutoBind
    private ChatUserService userService;

    @Override
    protected void execute() throws EzyBadRequestException {
        ChatUserModel chatUser = userService.getUserByUsername(
            user.getName()
        );
        validateUserPassword(oldPassword, chatUser.getPassword());
        userService.saveNewUserPassword(
            chatUser.getId(),
            newPassword
        );
        responseOk();
    }

    private void responseOk() {
        responseFactory.newArrayResponse()
            .command(UPDATE_PASSWORD)
            .user(user)
            .execute();
    }

    private void validateUserPassword(
        String requestPassword,
        String storeHashedPassword
    ) {
        String hashedPassword = EzySHA256.cryptUtfToLowercase(
            requestPassword
        );
        if (!hashedPassword.equals(storeHashedPassword)) {
            hashedPassword = BCrypt.hashpw(
                HASH_PASSWORD_SALT,
                requestPassword
            );
        }
        if (!hashedPassword.equals(storeHashedPassword)) {
            throw new EzyBadRequestException(
                ChatErrors.WRONG_PASSWORD,
                "wrong old password"
            );
        }
    }
}
