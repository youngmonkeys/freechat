package com.tvd12.freechat.common.mapstore;

import com.tvd12.ezydata.hazelcast.mapstore.EzyMongoDatastoreMapstore;
import com.tvd12.ezyfox.database.annotation.EzyMapstore;
import com.tvd12.freechat.common.constant.ChatEntities;
import com.tvd12.freechat.common.data.ChatUserToken;
import com.tvd12.freechat.common.repo.ChatUserTokenRepo;
import com.tvd12.freechat.common.repo.impl.ChatUserTokenRepoImpl;

import java.util.Map;

@EzyMapstore(ChatEntities.CHAT_USER_TOKEN)
public class ChatUserTokenMapstore extends EzyMongoDatastoreMapstore<String, ChatUserToken> {

	private ChatUserTokenRepo repo;
	
	@Override
	public void store(String key, ChatUserToken value) {
		repo.save(value);
	}

	@Override
	public void storeAll(Map<String, ChatUserToken> map) {
		repo.save(map.values());
	}
	
	@Override
	public ChatUserToken load(String key) {
		return repo.findByField("token", key);
	}

	@Override
	public void postInit() {
		ChatUserTokenRepoImpl repo = new ChatUserTokenRepoImpl();
		repo.setDatastore(datastore);
		this.repo = repo;
	}
}
