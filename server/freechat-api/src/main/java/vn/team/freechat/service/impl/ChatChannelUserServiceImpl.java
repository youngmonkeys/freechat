package vn.team.freechat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.io.EzyLists;
import com.tvd12.ezyfox.util.EzyHashMapSet;
import com.tvd12.ezyfox.util.EzyMapSet;

import lombok.Setter;
import vn.team.freechat.data.ChatChannelUser;
import vn.team.freechat.data.ChatChannelUsers;
import vn.team.freechat.repo.ChatChannelUserRepo;
import vn.team.freechat.service.ChatChannelUserService;

@Setter
@EzySingleton("channelUserService")
public class ChatChannelUserServiceImpl implements ChatChannelUserService {

	@EzyAutoBind
	protected ChatChannelUserRepo channelUserRepo;
	
	@Override
	public void saveChannelUsers(List<ChatChannelUser> channelUsers) {
		channelUserRepo.save(channelUsers);
	}
	
	@Override
	public ChatChannelUser getChannelUser(long channelId, String user) {
		return channelUserRepo.findByChannelIdAndUser(channelId, user);
	}
	
	@Override
	public ChatChannelUsers getChannelUsers(long channelId, String user) {
		List<ChatChannelUser> list = channelUserRepo.findByChannelId(channelId, user);
		Map<Long, Set<String>> map = mapChannelUsers(list);
		return new ChatChannelUsers(channelId, map.get(channelId));
	}
	
	@Override
	public List<Long> getChannelsIdOfUser(String user, int skip, int limit) {
		List<ChatChannelUser> list = channelUserRepo.findByUser(user, skip, limit);
		List<Long> answer = EzyLists.newArrayList(list, i -> i.getId().getChannelId());
		return answer;
	}
	
	@Override
	public List<ChatChannelUsers> getChannelsOfUser(String user, int skip, int limit) {
		List<Long> channelIds = getChannelsIdOfUser(user, skip, limit);
		List<ChatChannelUsers> answer = getChannelsOfUser(channelIds, user);
		return answer;
	}
	
	@Override
	public List<ChatChannelUsers> getChannelsOfUser(List<Long> channelIds, String user) {
		List<ChatChannelUser> list = channelUserRepo.findByChannelIds(channelIds, user);
		Map<Long, Set<String>> map = mapChannelUsers(list);
		List<ChatChannelUsers> answer = new ArrayList<>();
		for(Long channelId : map.keySet())
			answer.add(new ChatChannelUsers(channelId, map.get(channelId)));
		return answer;
	}
	
	protected Map<Long, Set<String>> mapChannelUsers(List<ChatChannelUser> list) {
		EzyMapSet<Long, String> map = new EzyHashMapSet<>();
		for(ChatChannelUser item : list)
			map.addItem(item.getId().getChannelId(), item.getId().getUser());
		return map;
	}

}
