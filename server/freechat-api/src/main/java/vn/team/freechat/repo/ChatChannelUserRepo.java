package vn.team.freechat.repo;

import java.util.List;

import vn.team.freechat.data.ChatChannelUser;

public interface ChatChannelUserRepo {

	void save(List<ChatChannelUser> entities);
	
	List<ChatChannelUser> findByUser(String user, int skip, int limit);

	List<ChatChannelUser> findByChannelId(long channelId, String user);
	
	ChatChannelUser findByChannelIdAndUser(long channelId, String user);
	
	List<ChatChannelUser> findByChannelIds(List<Long> channelIds, String user);

}
