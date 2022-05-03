package com.tvd12.freechat.service;

import com.tvd12.freechat.entity.ChatChannel;

import java.util.List;

public interface ChatChannelService {

    long newChannelId();

    void saveChannels(List<ChatChannel> channels);

}
