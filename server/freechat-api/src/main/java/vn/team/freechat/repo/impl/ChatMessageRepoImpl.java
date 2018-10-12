package vn.team.freechat.repo.impl;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.morphia.repository.EzyDatastoreRepository;

import vn.team.freechat.data.ChatMessage;
import vn.team.freechat.repo.ChatMessageRepo;

@EzySingleton("messageRepo")
public class ChatMessageRepoImpl 
		extends EzyDatastoreRepository<Long, ChatMessage>
		implements ChatMessageRepo{
	
	@Override
	protected Class<ChatMessage> getEntityType() {
		return ChatMessage.class;
	}
	

}
