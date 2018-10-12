package vn.team.freechat.service;

import vn.team.freechat.data.ChatGroup;
import java.util.List;

public interface ChatGroupService {

	void save(ChatGroup group);
	List<ChatGroup> getGroupByGroupName(String groupName);
	ChatGroup getPrivateGroupById(Long id);
}
