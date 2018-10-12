package vn.team.freechat.repo;


import com.tvd12.ezyfox.mongodb.EzyMongoRepository;

import vn.team.freechat.data.ChatGroup;

public interface ChatGroupRepo 
			extends EzyMongoRepository<Long , ChatGroup>  {
	
}
