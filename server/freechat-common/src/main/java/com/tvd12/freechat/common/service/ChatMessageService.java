package com.tvd12.freechat.common.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.common.constant.ChatEntities;
import com.tvd12.freechat.common.converter.ChatModelToEntityConverter;
import com.tvd12.freechat.common.entity.ChatMessage;
import com.tvd12.freechat.common.model.ChatSaveMessageModel;
import com.tvd12.freechat.common.repo.ChatMessageRepo;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepo messageRepo;
    private final ChatMaxIdService maxIdService;
    private final ChatModelToEntityConverter modelToEntityConverter;

    public void save(ChatSaveMessageModel message) {
        ChatMessage entity = modelToEntityConverter.toEntity(message);
        entity.setId(maxIdService.incrementAndGet(ChatEntities.CHAT_MESSAGE));
        messageRepo.save(entity);
    }
}
