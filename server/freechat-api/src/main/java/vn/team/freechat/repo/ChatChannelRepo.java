package vn.team.freechat.repo;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;

import vn.team.freechat.data.ChatChannel;

@EzyAutoImpl("channelRepo")
public interface ChatChannelRepo extends EzyMongoRepository<Long, ChatChannel> {
}
