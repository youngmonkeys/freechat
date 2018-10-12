package vn.team.freechat.service.impl;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;

import lombok.Setter;
import vn.team.freechat.common.service.ChatMaxIdService;
import vn.team.freechat.constant.ChatEntities;
import vn.team.freechat.data.ChatMessage;
import vn.team.freechat.repo.ChatMessageRepo;
import vn.team.freechat.service.ChatMessageService;

@Setter
@EzySingleton("messageService")
public class ChatMessageServiceImpl
		extends EzyLoggable
		implements ChatMessageService {

	@EzyAutoBind
	private ChatMessageRepo messageRepo;
	
	@EzyAutoBind
	private ChatMaxIdService maxIdService;
	

	
	private void presave(ChatMessage message) {
		long id = newEntityId();
		message.setId(id);
	}
	
	private long newEntityId() {
		return maxIdService.incrementAndGet(ChatEntities.CHAT_MESSAGE);
	}

	@Override
	public void save(ChatMessage message) {
		presave(message);
		messageRepo.save(message);
	}
	
}
