package com.tvd12.freechat.test.mongodb;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.freechat.test.entity.ChatMessage;

@EzyRepository
public interface MongoMessageRepository
    extends EzyMongoRepository<Long, ChatMessage> {

}
