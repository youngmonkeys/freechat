package com.tvd12.freechat.app.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.freechat.app.entity.ChatContact;
import com.tvd12.freechat.app.entity.ChatContactId;

import java.util.List;

@EzyRepository("contactRepo")
public interface ChatContactRepo
    extends EzyMongoRepository<ChatContactId, ChatContact> {

    @EzyQuery("{$or:[{'_id.user1st': ?0}, {'_id.user2nd' : ?0}]}")
    List<ChatContact> findContactsByActor(
        String actor, Next next);

    @EzyQuery("{$or:[{'_id.user1st': ?0}, {'_id.user2nd': ?0}]}")
    int countContactByActor(String actor);
}
