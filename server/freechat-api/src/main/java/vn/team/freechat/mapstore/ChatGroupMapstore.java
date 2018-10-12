package vn.team.freechat.mapstore;

import java.util.Map;

import com.tvd12.ezyfox.database.annotation.EzyMapstore;
import com.tvd12.ezyfox.hazelcast.mapstore.EzyMongoDatastoreMapstore;

import vn.team.freechat.constant.ChatEntities;
import vn.team.freechat.data.ChatGroup;
import vn.team.freechat.repo.ChatGroupRepo;
import vn.team.freechat.repo.impl.ChatGroupRepoImpl;

@EzyMapstore(ChatEntities.CHAT_GROUP)
public class ChatGroupMapstore extends EzyMongoDatastoreMapstore<Long, ChatGroup> {

	private ChatGroupRepo chatGroupRepo;
	@Override
	public void store(Long key, ChatGroup value) {
		chatGroupRepo.save(value);
	}
	
	@Override
	public void storeAll(Map<Long, ChatGroup> map) {
		chatGroupRepo.save(map.values());
	}

	@Override
	public ChatGroup load(Long key) {
		return chatGroupRepo.findByField("id", key);
	}
	
	@Override
	public void postInit() {
		ChatGroupRepoImpl chatGroupRepo = new ChatGroupRepoImpl();
		chatGroupRepo.setDatastore(datastore);
		this.chatGroupRepo = chatGroupRepo;
	}

	

	

}
