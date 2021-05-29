package com.tvd12.freechat.test.mysql;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.freechat.test.entity.ChatMessage;

@EzyRepository
public interface MySQLMessageRepository 
		extends EzyDatabaseRepository<Long, ChatMessage> {

}
