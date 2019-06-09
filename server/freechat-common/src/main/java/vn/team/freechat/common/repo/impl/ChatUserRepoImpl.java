package vn.team.freechat.common.repo.impl;

import java.util.List;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.morphia.repository.EzyDatastoreRepository;

import dev.morphia.query.FindOptions;
import dev.morphia.query.Query;
import dev.morphia.query.internal.MorphiaCursor;
import vn.team.freechat.common.data.ChatUser;
import vn.team.freechat.common.repo.ChatUserRepo;

@EzySingleton("userRepo")
public class ChatUserRepoImpl
		extends EzyDatastoreRepository<Long, ChatUser> 
		implements ChatUserRepo {

	@Override
	public ChatUser findByUsername(String username) {
		return findByField("username", username);
	}
	
	@Override
	public List<ChatUser> findSuggestionUsers(String owner, int skip, int limit) {
		Query<ChatUser> query = newQuery()
				.field("username").notEqual(owner);
		FindOptions opts = new FindOptions()
				.skip(skip)
				.limit(limit);
		MorphiaCursor<ChatUser> cursor = query.find(opts);
		List<ChatUser> list = cursor.toList();
		return list;
	}
	
	@Override
	protected Class<ChatUser> getEntityType() {
		return ChatUser.class;
	}
	
	
}
