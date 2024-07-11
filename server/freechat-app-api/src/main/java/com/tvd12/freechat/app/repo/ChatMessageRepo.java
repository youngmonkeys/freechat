package com.tvd12.freechat.app.repo;


import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.freechat.app.entity.ChatMessage;

@EzyAutoImpl("messageRepo")
public interface ChatMessageRepo
    extends EzyMongoRepository<Long, ChatMessage> {}
