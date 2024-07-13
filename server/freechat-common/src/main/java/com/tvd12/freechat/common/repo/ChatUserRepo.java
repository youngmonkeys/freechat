package com.tvd12.freechat.common.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.freechat.common.entity.ChatUser;

import java.util.Collection;
import java.util.List;

@EzyRepository
public interface ChatUserRepo extends EzyMongoRepository<Long, ChatUser> {

    List<ChatUser> findByUsernameIn(
        Collection<String> usernames
    );

    @EzyQuery(
        "{$and: [{'_id': {$ne: ?0}}, {'_id': {$gt : ?1}}]}"
    )
    List<ChatUser> findByIdNeAndIdGt(
        long exclusiveUserId,
        long userIdGt,
        Next next
    );

    @EzyQuery(
        "{$and: [{'_id': {$ne: ?0}}, " +
            "{'_id': {$gt : ?1}}, " +
            "{'username': {$regex : ?2}}]}"
    )
    List<ChatUser> findByIdNeAndIdGtAndRegex(
        long exclusiveUserId,
        long userIdGt,
        String regex,
        Next next
    );
}
