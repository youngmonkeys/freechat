package vn.team.freechat.common.service.impl;

import com.hazelcast.core.HazelcastInstance;
import com.tvd12.ezydata.hazelcast.factory.EzyMapTransactionFactory;
import com.tvd12.ezydata.hazelcast.service.EzyTransactionalMaxIdService;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import vn.team.freechat.common.service.ChatMaxIdService;

@EzySingleton("maxIdService")
public class ChatMaxIdServiceImpl 
		extends EzyTransactionalMaxIdService
		implements ChatMaxIdService {

	@EzyAutoBind("hzInstance")
	public ChatMaxIdServiceImpl(HazelcastInstance hazelcastInstance) {
		super(hazelcastInstance);
	}

	@EzyAutoBind
	@Override
	public void setMapTransactionFactory(EzyMapTransactionFactory mapTransactionFactory) {
		super.setMapTransactionFactory(mapTransactionFactory);
	}

}
