package com.tvd12.freechat.common.service;

import java.util.List;

import com.tvd12.freechat.common.entity.ChatUser;

public interface ChatUserService {

	void saveUser(ChatUser user);
	
	ChatUser getUser(String username);
	
	ChatUser createUser(String username, String password);

	List<ChatUser> getSearchUsers(String keyword, String owner, int skip, int limit);
	
	List<ChatUser> getSuggestionUsers(String owner, int skip, int limit);
	
}
