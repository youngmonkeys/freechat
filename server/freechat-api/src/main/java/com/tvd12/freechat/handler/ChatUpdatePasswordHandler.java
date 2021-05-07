package com.tvd12.freechat.handler;

import static com.tvd12.freechat.constant.ChatCommands.UPDATE_PASSWORD;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfox.sercurity.EzySHA256;
import com.tvd12.freechat.common.entity.ChatUser;
import com.tvd12.freechat.common.service.ChatUserService;
import com.tvd12.freechat.constant.ChatErrors;

import lombok.Setter;


@Setter
@EzyPrototype
@EzyObjectBinding
@EzyRequestListener(UPDATE_PASSWORD)
public class ChatUpdatePasswordHandler extends ChatClientRequestHandler implements EzyDataBinding {

    // EzyValue: get value from object with key "oldPassword"
    // E.g.: request = [APP_REQUEST, [APP_ID, {"oldPassword": "test1234"}]]]
    @EzyValue
    private String oldPassword;

    @EzyValue
    private String newPassword;

    @EzyAutoBind
    private ChatUserService userService;

    @Override
    protected void execute() throws EzyBadRequestException {
        ChatUser chatUser = userService.getUser(user.getName());
        String cryptOldPassword = EzySHA256.cryptUtfToLowercase(oldPassword);

        if (!chatUser.getPassword().equals(cryptOldPassword)) {
            throw new EzyBadRequestException(ChatErrors.WRONG_PASSWORD, "wrong old password");
        }

        chatUser.setPassword(EzySHA256.cryptUtfToLowercase(newPassword));
        userService.saveUser(chatUser);
        responseOk();
    }

    private void responseOk() {
        responseFactory.newArrayResponse()
                .command(UPDATE_PASSWORD)
                .user(user)
                .execute();
    }
}
