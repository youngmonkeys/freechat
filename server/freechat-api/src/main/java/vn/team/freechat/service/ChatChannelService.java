package vn.team.freechat.service;

import java.util.List;

import vn.team.freechat.data.ChatChannel;

public interface ChatChannelService {

	long newChannelId();
	
	void saveChannels(List<ChatChannel> channels);
	
}
