package vn.team.freechat.repo;


import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.annotation.EzyAutoImpl;

import vn.team.freechat.data.ChatMessage;

@EzyAutoImpl("messageRepo")
public interface ChatMessageRepo 
	extends EzyMongoRepository<Long, ChatMessage> {
	
	
}
