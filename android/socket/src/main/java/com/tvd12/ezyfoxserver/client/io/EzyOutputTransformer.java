package com.tvd12.ezyfoxserver.client.io;

public interface EzyOutputTransformer {

	@SuppressWarnings("rawtypes")
	Object transform(Object value, Class type);
	
}
