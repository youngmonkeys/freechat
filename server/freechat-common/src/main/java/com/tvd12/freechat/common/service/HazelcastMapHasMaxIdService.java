package com.tvd12.freechat.common.service;

import com.tvd12.ezydata.hazelcast.service.EzyBeanSimpleHazelcastMapService;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.database.service.EzyMaxIdService;

import lombok.Setter;

public abstract class HazelcastMapHasMaxIdService<K,V> 
		extends EzyBeanSimpleHazelcastMapService<K, V> {

	@EzyAutoBind
	@Setter
	protected EzyMaxIdService maxIdService;
	
	protected long newId(String key) {
		return maxIdService.incrementAndGet(key);
	}
	
}
