package com.tvd12.ezyfoxserver.client.codec;

public interface EzyDecoderCreator {

	Object newDecoder(int maxRequestSize);
	
}
