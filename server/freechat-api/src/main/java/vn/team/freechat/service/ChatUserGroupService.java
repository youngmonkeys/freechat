package vn.team.freechat.service;

import java.util.List;

import vn.team.freechat.data.ChatUserGroup;

public interface ChatUserGroupService {
	
	void save(ChatUserGroup userGroup);
	
	List<String> getUsersByGroupId(long groupId);
	
	List<ChatUserGroup> getUserGroupByUserName(String userName);
}
