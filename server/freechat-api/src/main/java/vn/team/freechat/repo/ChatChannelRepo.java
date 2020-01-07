package vn.team.freechat.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.annotation.EzyAutoImpl;

import vn.team.freechat.data.ChatChannel;

@EzyAutoImpl("channelRepo")
public interface ChatChannelRepo extends EzyMongoRepository<Long, ChatChannel> {
}
