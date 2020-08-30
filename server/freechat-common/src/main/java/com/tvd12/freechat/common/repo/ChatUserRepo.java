package com.tvd12.freechat.common.repo;

import java.util.List;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.freechat.common.data.ChatUser;

public interface ChatUserRepo extends EzyMongoRepository<Long, ChatUser> {

	List<ChatUser> findByUsername(String keyword, String owner, int skip, int limit);
	
	List<ChatUser> findSuggestionUsers(String owner, int skip, int limit);
	
}
