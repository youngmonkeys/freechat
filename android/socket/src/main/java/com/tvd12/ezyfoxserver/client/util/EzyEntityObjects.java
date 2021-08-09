package com.tvd12.ezyfoxserver.client.util;

import com.tvd12.ezyfoxserver.client.entity.EzyObject;
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory;

import java.util.Map;

@SuppressWarnings("rawtypes")
public final class EzyEntityObjects {

	private EzyEntityObjects() {
	}

	public static EzyObject newObject(Object key, Object value) {
		EzyObject obj = EzyEntityFactory.newObject();
		obj.put(key, value);
		return obj;
	}
	
	public static EzyObject newObject(Map map) {
		EzyObject obj = EzyEntityFactory.newObject();
		obj.putAll(map);
		return obj;
	}
	
}
