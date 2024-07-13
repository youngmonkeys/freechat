package com.tvd12.freechat.common.converter;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.common.entity.ChatContact;
import com.tvd12.freechat.common.entity.ChatUser;
import com.tvd12.freechat.common.model.ChatContactModel;
import com.tvd12.freechat.common.model.ChatUserModel;

@EzySingleton
public class ChatEntityToModelConverter {

    public ChatUserModel toModel(ChatUser user) {
        if (user == null) {
            return null;
        }
        return ChatUserModel.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .fullName(user.getFullName())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }

    public ChatContactModel toModel(ChatContact entity) {
        if (entity == null) {
            return null;
        }
        return ChatContactModel.builder()
            .user1stId(entity.getId().getUser1stId())
            .user2ndId(entity.getId().getUser2ndId())
            .channelId(entity.getChannelId())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }
}
