package com.tvd12.freechat.common.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.freechat.common.entity.ChatUser;

import java.util.List;
import java.util.Set;

@EzyRepository("userRepo")
public interface ChatUserRepo extends EzyMongoRepository<Long, ChatUser> {

    @EzyQuery("{$and: [{'username': {$nin: ?0}}, {'username': {$regex : ?1}}]}")
    List<ChatUser> findByUsernameRegex(
        Set<String> excludeUsers, String keyword, Next next);

    @EzyQuery("{'username': {$nin: ?0}}")
    List<ChatUser> findSuggestionUsers(Set<String> excludeUsers, Next next);

}
