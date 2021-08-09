package com.tvd12.ezyfoxserver.client.codec;

import java.nio.ByteBuffer;

public interface EzyObjectDeserializer {
	
	<T> T deserialize(ByteBuffer buffer);
	
	<T> T read(ByteBuffer buffer);
	
	<T> T read(byte[] buffer);

	<T> T deserialize(byte[] data);
	
	<T> T deserialize(String text);
	
}
