package vn.team.freechat.common.repo;

import java.util.List;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;

import vn.team.freechat.common.data.ChatUser;

public interface ChatUserRepo extends EzyMongoRepository<Long, ChatUser> {

	ChatUser findByUsername(String username);
	
	List<ChatUser> findSuggestionUsers(String owner, int skip, int limit);
	
}
