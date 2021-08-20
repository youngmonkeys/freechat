package com.tvd12.freechat.common.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.freechat.common.entity.ChatUserFirebaseToken;
import java.util.Collection;
import java.util.List;

@EzyRepository
public interface ChatUserFirebaseTokenRepo extends EzyMongoRepository<String, ChatUserFirebaseToken> {
    List<ChatUserFirebaseToken> findByUsernameIn(Collection<String> usernames);
}
