package com.tvd12.freechat.common.repo;

import java.util.List;

import com.tvd12.ezydata.database.annotation.EzyQuery;
import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.freechat.common.entity.ChatUser;

@EzyRepository("userRepo")
public interface ChatUserRepo extends EzyMongoRepository<Long, ChatUser> {

	@EzyQuery("{$and: [{'username': {$ne: ?0}}, {'username': {$regex : ?1}}]}")
	List<ChatUser> findByUsernameRegex(String keyword, String owner, Next next);
	
	@EzyQuery("{'username': {$ne: ?0}}")
	List<ChatUser> findSuggestionUsers(String owner, Next next);
	
}
