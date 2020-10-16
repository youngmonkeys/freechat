package com.tvd12.freechat.common.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.freechat.common.data.ChatUserToken;

public interface ChatUserTokenRepo extends EzyMongoRepository<String, ChatUserToken> {
}
