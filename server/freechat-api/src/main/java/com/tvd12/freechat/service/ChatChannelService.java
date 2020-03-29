package com.tvd12.freechat.service;

import java.util.List;

import com.tvd12.freechat.data.ChatChannel;

public interface ChatChannelService {

	long newChannelId();
	
	void saveChannels(List<ChatChannel> channels);
	
}
