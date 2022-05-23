package com.tvd12.freechat.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.freechat.entity.ChatChannel;

@EzyRepository("channelRepo")
public interface ChatChannelRepo
    extends EzyMongoRepository<Long, ChatChannel> {}
