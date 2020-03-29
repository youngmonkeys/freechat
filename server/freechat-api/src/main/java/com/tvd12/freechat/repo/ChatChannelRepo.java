package com.tvd12.freechat.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.freechat.data.ChatChannel;

@EzyAutoImpl("channelRepo")
public interface ChatChannelRepo extends EzyMongoRepository<Long, ChatChannel> {
}
