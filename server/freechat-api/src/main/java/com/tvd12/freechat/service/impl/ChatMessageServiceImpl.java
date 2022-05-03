package com.tvd12.freechat.service.impl;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.freechat.common.service.ChatMaxIdService;
import com.tvd12.freechat.constant.ChatEntities;
import com.tvd12.freechat.entity.ChatMessage;
import com.tvd12.freechat.repo.ChatMessageRepo;
import com.tvd12.freechat.service.ChatMessageService;
import lombok.Setter;

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
