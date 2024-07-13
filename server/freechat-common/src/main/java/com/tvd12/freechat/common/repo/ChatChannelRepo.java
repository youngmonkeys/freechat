package com.tvd12.freechat.common.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.freechat.common.entity.ChatChannel;

@EzyRepository
public interface ChatChannelRepo
    extends EzyMongoRepository<Long, ChatChannel> {}
