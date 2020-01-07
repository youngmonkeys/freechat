package vn.team.freechat.common.mapstore;

import java.util.Map;

import com.tvd12.ezydata.hazelcast.mapstore.EzyMongoDatastoreMapstore;
import com.tvd12.ezyfox.database.annotation.EzyMapstore;

import vn.team.freechat.common.constant.ChatEntities;
import vn.team.freechat.common.data.ChatUser;
import vn.team.freechat.common.repo.ChatUserRepo;
import vn.team.freechat.common.repo.impl.ChatUserRepoImpl;

@EzyMapstore(ChatEntities.CHAT_USER)
public class ChatUserMapstore extends EzyMongoDatastoreMapstore<String, ChatUser> {

	private ChatUserRepo repo;
	
	@Override
	public void store(String key, ChatUser value) {
		repo.save(value);
	}

	@Override
	public void storeAll(Map<String, ChatUser> map) {
		repo.save(map.values());
	}
	
	@Override
	public ChatUser load(String key) {
		return repo.findByField("username", key);
	}

	@Override
	public void postInit() {
		ChatUserRepoImpl repo = new ChatUserRepoImpl();
		repo.setDatastore(datastore);
		this.repo = repo;
	}
	
}
