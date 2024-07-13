package com.tvd12.freechat.common.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.freechat.common.entity.ChatChannelUser;
import com.tvd12.freechat.common.entity.ChatChannelUserId;

import java.util.Collection;
import java.util.List;

@EzyRepository
public interface ChatChannelUserRepo
    extends EzyMongoRepository<ChatChannelUserId, ChatChannelUser> {

    @EzyQuery(
        "{$and: [{'_id.channelId': {$in: ?0}}, " +
            "{'_id.user': {$ne: ?1}}]}"
    )
    List<ChatChannelUser> findByChannelIdsAndUserIdNeq(
        Collection<Long> channelIds,
        long exclusiveUserId
    );
}
