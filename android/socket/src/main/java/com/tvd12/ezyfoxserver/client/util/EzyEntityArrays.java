package com.tvd12.ezyfoxserver.client.util;

import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory;

import java.util.List;

@SuppressWarnings("rawtypes")
public final class EzyEntityArrays {

	private EzyEntityArrays() {
	}
	
	public static EzyArray newArray() {
		return EzyEntityFactory.newArray();
	}
	
	public static EzyArray newArray(List list) {
		EzyArray array = EzyEntityFactory.newArray();
		array.add(list);
		return array;
	}
	
	public static EzyArray newArray(Object... args) {
		EzyArray array = EzyEntityFactory.newArray();
		array.add(args);
		return array;
	}
	
}
