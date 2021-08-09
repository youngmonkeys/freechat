package com.tvd12.ezyfoxserver.client.util;

import com.tvd12.ezyfoxserver.client.constant.EzyHasIntId;
import com.tvd12.ezyfoxserver.client.function.EzyFunction;

public final class EzyEnums {

	private EzyEnums() {
	}
	
	public static <T extends EzyHasIntId> T valueOf(T[] values, int id) {
		return valueOf(values, id, new EzyFunction<T, Object>() {
			@Override
			public Object apply(T t) {
				return t.getId();
			}
		});
	}
	
	public static <T> T valueOf(
			T[] values, Object id, EzyFunction<T, Object> idFetcher) {
		for(T v : values) {
			Object vid = idFetcher.apply(v);
            if(vid.equals(id)) {
                return v;
            }
		}
        throw new IllegalArgumentException("has no enum value with id = " + id);
	}
}
