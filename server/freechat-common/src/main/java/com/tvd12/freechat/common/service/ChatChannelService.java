package com.tvd12.freechat.common.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.common.constant.ChatEntities;
import com.tvd12.freechat.common.converter.ChatModelToEntityConverter;
import com.tvd12.freechat.common.entity.ChatChannel;
import com.tvd12.freechat.common.repo.ChatChannelRepo;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class ChatChannelService {

    private final ChatChannelRepo channelRepo;
    private final ChatMaxIdService maxIdService;
    private final ChatModelToEntityConverter modelToEntityConverter;

    public long createChannel(
        long creatorId,
        String name
    ) {
        long channelId = maxIdService.incrementAndGet(
            ChatEntities.CHAT_CHANNEL
        );
        ChatChannel channel = modelToEntityConverter
            .toContactChatChannelEntity(
                creatorId,
                name
            );
        channel.setId(channelId);
        channelRepo.save(channel);
        return channelId;
    }

    public void saveChannelLastChattedAt(long channelId) {
        ChatChannel entity = channelRepo.findById(
            channelId
        );
        if (entity != null) {
            entity.setLastChattedAt(System.currentTimeMillis());
            channelRepo.save(entity);
        }
    }
}
