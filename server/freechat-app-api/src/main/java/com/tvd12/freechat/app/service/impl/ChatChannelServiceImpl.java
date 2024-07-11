package com.tvd12.freechat.app.service.impl;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.common.service.ChatMaxIdService;
import com.tvd12.freechat.app.constant.ChatEntities;
import com.tvd12.freechat.app.entity.ChatChannel;
import com.tvd12.freechat.app.repo.ChatChannelRepo;
import com.tvd12.freechat.app.service.ChatChannelService;
import lombok.Setter;

import java.util.List;

@Setter
@EzySingleton("channelService")
public class ChatChannelServiceImpl implements ChatChannelService {

    @EzyAutoBind
    protected ChatChannelRepo channelRepo;
    @EzyAutoBind
    private ChatMaxIdService maxIdService;

    @Override
    public long newChannelId() {
        return maxIdService.incrementAndGet(ChatEntities.CHAT_CHANNEL);
    }

    @Override
    public void saveChannels(List<ChatChannel> channels) {
        channelRepo.save(channels);
    }
}
