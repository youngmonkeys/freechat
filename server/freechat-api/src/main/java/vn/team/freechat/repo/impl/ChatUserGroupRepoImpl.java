package vn.team.freechat.repo.impl;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.morphia.repository.EzyDatastoreRepository;

import vn.team.freechat.data.ChatUserGroup;
import vn.team.freechat.repo.ChatUserGroupRepo;

@EzySingleton("userChatGroupRepo")
public class ChatUserGroupRepoImpl 
		extends EzyDatastoreRepository<Long, ChatUserGroup>
		implements ChatUserGroupRepo {
	
	@Override
	protected Class<ChatUserGroup> getEntityType() {
		return ChatUserGroup.class;
	}
	
}
