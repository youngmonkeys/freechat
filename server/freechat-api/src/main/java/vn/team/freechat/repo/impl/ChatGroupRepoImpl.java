package vn.team.freechat.repo.impl;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.morphia.repository.EzyDatastoreRepository;

import vn.team.freechat.data.ChatGroup;
import vn.team.freechat.repo.ChatGroupRepo;

@EzySingleton("chatGroupRepo")
public class ChatGroupRepoImpl 
		extends EzyDatastoreRepository<Long, ChatGroup>
		implements ChatGroupRepo {
	
	@Override
	protected Class<ChatGroup> getEntityType() {
		return ChatGroup.class;
	}
	
}
