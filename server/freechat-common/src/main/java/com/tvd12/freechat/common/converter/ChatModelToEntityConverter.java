package com.tvd12.freechat.common.converter;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.common.entity.*;
import com.tvd12.freechat.common.model.ChatSaveChannelUserModel;
import com.tvd12.freechat.common.model.ChatSaveContactModel;
import com.tvd12.freechat.common.model.ChatSaveMessageModel;

@EzySingleton
public class ChatModelToEntityConverter {

    public ChatMessage toEntity(ChatSaveMessageModel model) {
        ChatMessage entity = new ChatMessage();
        entity.setMessage(model.getMessage());
        entity.setChannelId(model.getChannelId());
        entity.setSenderId(model.getSenderId());
        entity.setClientMessageId(model.getClientMessageId());
        entity.setStatus(model.getStatus());
        long now = System.currentTimeMillis();
        entity.setSentAt(now);
        entity.setReadAt(model.getReadAt());
        return entity;
    }

    public ChatChannelUser toEntity(ChatSaveChannelUserModel model) {
        ChatChannelUser entity = new ChatChannelUser();
        entity.setId(
            new ChatChannelUserId(
                model.getChannelId(),
                model.getUserId()
            )
        );
        return entity;
    }

    public ChatContact toEntity(ChatSaveContactModel model) {
        ChatContact entity = new ChatContact();
        entity.setId(
            new ChatContactId(
                model.getUser1stId(),
                model.getUser2ndId()
            )
        );
        entity.setChannelId(model.getChannelId());
        long now = System.currentTimeMillis();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        return entity;
    }

    public ChatChannel toContactChatChannelEntity(
        long creatorUserId,
        String name
    ) {
        ChatChannel channel = new ChatChannel();
        channel.setCreatorUserId(creatorUserId);
        channel.setName(name);
        channel.setType(ChatChannelType.CONTACT);
        long now = System.currentTimeMillis();
        channel.setCreatedAt(now);
        channel.setUpdatedAt(now);
        return channel;
    }
}
