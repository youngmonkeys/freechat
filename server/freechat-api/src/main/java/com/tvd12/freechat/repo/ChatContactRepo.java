package com.tvd12.freechat.repo;

import java.util.List;

import com.tvd12.ezydata.database.annotation.EzyQuery;
import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.freechat.entity.ChatContact;
import com.tvd12.freechat.entity.ChatContactId;

@EzyRepository("contactRepo")
public interface ChatContactRepo 
		extends EzyMongoRepository<ChatContactId, ChatContact> {

	@EzyQuery("{$or:[{'_id.user1st': ?0}, {'_id.user2nd' : ?1}]}")
	List<ChatContact> findContactsByActor(
			String actor1st, String actor2nd, Next next);
	
	@EzyQuery("{$or:[{'_id.user1st': ?0}, {'_id.user2nd': ?1}]}")
	int countContactByActor(String actor1st, String actor2nd);
	
}
