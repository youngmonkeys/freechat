package vn.team.freechat.repo;


import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;

import vn.team.freechat.data.ChatMessage;

@EzyAutoImpl
public interface ChatMessageRepo 
	extends EzyMongoRepository<Long, ChatMessage> {
	
	
}
