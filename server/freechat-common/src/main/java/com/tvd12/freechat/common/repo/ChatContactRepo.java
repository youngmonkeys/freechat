package com.tvd12.freechat.common.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.freechat.common.entity.ChatContact;
import com.tvd12.freechat.common.entity.ChatContactId;

@EzyRepository
public interface ChatContactRepo
    extends EzyMongoRepository<ChatContactId, ChatContact> {}
