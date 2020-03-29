package com.tvd12.freechat.common.service;

import java.util.List;

import com.tvd12.ezyfox.function.EzyApply;
import com.tvd12.freechat.common.data.ChatNewUser;
import com.tvd12.freechat.common.data.ChatUser;

public interface ChatUserService {

	void saveUser(ChatUser user);
	
	ChatUser getUser(String username);
	
	ChatNewUser createUser(String username, EzyApply<ChatUser> applier);

	List<ChatUser> getSearchUsers(String owner, int skip, int limit);
	
	List<ChatUser> getSuggestionUsers(String owner, int skip, int limit);
	
}
