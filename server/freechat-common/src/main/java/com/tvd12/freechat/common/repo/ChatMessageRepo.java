package com.tvd12.freechat.common.repo;


import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.freechat.common.entity.ChatMessage;

@EzyAutoImpl
public interface ChatMessageRepo
    extends EzyMongoRepository<Long, ChatMessage> {}
