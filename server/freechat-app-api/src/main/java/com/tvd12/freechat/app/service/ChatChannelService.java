package com.tvd12.freechat.app.service;

import com.tvd12.freechat.app.entity.ChatChannel;

import java.util.List;

public interface ChatChannelService {

    long newChannelId();

    void saveChannels(List<ChatChannel> channels);
}
