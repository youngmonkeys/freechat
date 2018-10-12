package vn.team.freechat.mapstore;

import java.util.Map;

import com.tvd12.ezyfox.database.annotation.EzyMapstore;
import com.tvd12.ezyfox.hazelcast.mapstore.EzyMongoDatastoreMapstore;

import vn.team.freechat.constant.ChatEntities;
import vn.team.freechat.data.ChatUserGroup;
import vn.team.freechat.data.ChatUserGroup.DataId;
import vn.team.freechat.repo.ChatUserGroupRepo;
import vn.team.freechat.repo.impl.ChatUserGroupRepoImpl;

@EzyMapstore(ChatEntities.CHAT_USER_GROUP)
public class ChatUserGroupMapstore extends EzyMongoDatastoreMapstore<DataId, ChatUserGroup> {

	private ChatUserGroupRepo userChatGroupRepo;
	@Override
	public void store(DataId key, ChatUserGroup value) {
		userChatGroupRepo.save(value);
	}
	
	@Override
	public void storeAll(Map<DataId, ChatUserGroup> map) {
		userChatGroupRepo.save(map.values());
	}

	@Override
	public ChatUserGroup load(DataId key) {
		return userChatGroupRepo.findByField("id", key);
	}
	
	@Override
	public void postInit() {
		ChatUserGroupRepoImpl userChatGroupRepo = new ChatUserGroupRepoImpl();
		userChatGroupRepo.setDatastore(datastore);
		this.userChatGroupRepo = userChatGroupRepo;
	}

	

	

}
