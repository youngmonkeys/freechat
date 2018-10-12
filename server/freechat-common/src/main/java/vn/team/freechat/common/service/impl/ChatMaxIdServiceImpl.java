package vn.team.freechat.common.service.impl;

import com.hazelcast.core.HazelcastInstance;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.hazelcast.factory.EzyMapTransactionFactory;
import com.tvd12.ezyfox.hazelcast.service.EzySimpleMaxIdService;

import vn.team.freechat.common.service.ChatMaxIdService;

@EzySingleton("maxIdService")
public class ChatMaxIdServiceImpl 
		extends EzySimpleMaxIdService
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
