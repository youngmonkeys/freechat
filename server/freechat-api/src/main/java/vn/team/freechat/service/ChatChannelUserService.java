package vn.team.freechat.service;

import java.util.List;

import vn.team.freechat.data.ChatChannelUser;
import vn.team.freechat.data.ChatChannelUsers;

public interface ChatChannelUserService {

	void saveChannelUsers(List<ChatChannelUser> channelUsers);
	
	ChatChannelUser getChannelUser(long channelId, String user);
	
	ChatChannelUsers getChannelUsers(long channelId, String user);
	
	List<Long> getChannelsIdOfUser(String user, int skip, int limit);

	List<ChatChannelUsers> getChannelsOfUser(String user, int skip, int limit);
	
	List<ChatChannelUsers> getChannelsOfUser(List<Long> channelIds, String user);
	
}
