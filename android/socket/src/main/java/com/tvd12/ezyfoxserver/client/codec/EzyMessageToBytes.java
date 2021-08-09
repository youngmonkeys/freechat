package com.tvd12.ezyfoxserver.client.codec;

public interface EzyMessageToBytes {

	<T> T convert(EzyMessage message);
	
}
