package com.tvd12.freechat.app.converter;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.app.response.ChatChannelUsersResponse;
import com.tvd12.freechat.app.response.ChatContactUserResponse;
import com.tvd12.freechat.common.model.ChatContactUserModel;
import com.tvd12.freechat.common.model.ChatUserModel;

@EzySingleton
public class ChatAppModelToResponseConverter {

    public ChatContactUserResponse toChatContactUserResponse(
        ChatUserModel model
    ) {
        return ChatContactUserResponse.builder()
            .username(model.getUsername())
            .fullName(model.getFullName())
            .build();
    }

    public ChatChannelUsersResponse toChatChannelUsersResponse(
        ChatContactUserModel model
    ) {
        return new ChatChannelUsersResponse(
            model.getChannelId(),
            model.getUser().getUsername()
        );
    }
}
