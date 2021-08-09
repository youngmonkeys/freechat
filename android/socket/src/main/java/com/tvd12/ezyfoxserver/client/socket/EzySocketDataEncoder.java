package com.tvd12.ezyfoxserver.client.socket;

public interface EzySocketDataEncoder {

	byte[] encode(Object data) throws Exception;
	
}
