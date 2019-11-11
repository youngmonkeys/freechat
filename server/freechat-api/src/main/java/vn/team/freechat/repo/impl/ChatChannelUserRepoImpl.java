package vn.team.freechat.repo.impl;

import java.util.List;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.morphia.repository.EzyDatastoreRepository;

import dev.morphia.query.Query;
import dev.morphia.query.internal.MorphiaCursor;
import vn.team.freechat.data.ChatChannelUser;
import vn.team.freechat.data.ChatChannelUserId;
import vn.team.freechat.repo.ChatChannelUserRepo;

@EzySingleton("channelUserRepo")
public class ChatChannelUserRepoImpl
		extends EzyDatastoreRepository<ChatChannelUserId, ChatChannelUser>
		implements ChatChannelUserRepo {

	@Override
	public void save(List<ChatChannelUser> entities) {
		datastore.save(entities);
	}

	@Override
	public List<ChatChannelUser> findByUser(String user, int skip, int limit) {
		List<ChatChannelUser> list = findListByField("id.user", user, skip, limit);
		return list;
	}

	@Override
	public List<ChatChannelUser> findByChannelId(long channelId, String user) {
		Query<ChatChannelUser> query = newQuery();
		query.and(
				query.criteria("id.channelId").equal(channelId),
				query.criteria("id.user").notEqual(user)
		);
		MorphiaCursor<ChatChannelUser> cursor = query.find();
		List<ChatChannelUser> list = cursor.toList();
		return list;
	}
	
	@Override
	public ChatChannelUser findByChannelIdAndUser(long channelId, String user) {
		ChatChannelUser one = findById(new ChatChannelUserId(channelId, user));
		return one;
	}

	@Override
	public List<ChatChannelUser> findByChannelIds(List<Long> channelIds, String user) {
		Query<ChatChannelUser> query = newQuery();
		query.and(
				query.criteria("id.channelId").in(channelIds),
				query.criteria("id.user").notEqual(user)
		);
		MorphiaCursor<ChatChannelUser> cursor = query.find();
		List<ChatChannelUser> list = cursor.toList();
		return list;
	}

}
