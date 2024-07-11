package com.tvd12.freechat.app.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.freechat.app.entity.ChatChannelUser;
import com.tvd12.freechat.app.entity.ChatChannelUserId;

import java.util.List;

@EzyRepository("channelUserRepo")
public interface ChatChannelUserRepo
    extends EzyMongoRepository<ChatChannelUserId, ChatChannelUser> {

    @EzyQuery("{'_id.user': ?0}")
    List<ChatChannelUser> findByUser(String user, Next next);

    @EzyQuery("{$and: [{'_id.channelId': ?0}, {'_id.user': {$ne: ?1}}]}")
    List<ChatChannelUser> findByChannelId(long channelId, String user);

    @EzyQuery("{$and: [{'_id.channelId': {$in: ?0}}, {'_id.user': {$ne: ?1}}]}")
    List<ChatChannelUser> findByChannelIds(List<Long> channelIds, String user);
}
