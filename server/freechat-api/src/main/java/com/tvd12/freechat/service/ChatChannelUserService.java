package com.tvd12.freechat.service;

import java.util.List;

import com.tvd12.freechat.data.ChatChannelUser;
import com.tvd12.freechat.data.ChatChannelUsers;

public interface ChatChannelUserService {

	void saveChannelUsers(List<ChatChannelUser> channelUsers);
	
	ChatChannelUser getChannelUser(long channelId, String user);
	
	ChatChannelUsers getChannelUsers(long channelId, String user);
	
	List<Long> getChannelsIdOfUser(String user, int skip, int limit);

	List<ChatChannelUsers> getChannelsOfUser(String user, int skip, int limit);
	
	List<ChatChannelUsers> getChannelsOfUser(List<Long> channelIds, String user);
	
}
