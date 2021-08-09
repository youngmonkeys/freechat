package com.tvd12.ezyfoxserver.client.codec;

import java.nio.ByteBuffer;

public abstract class EzyAbstractToBytesSerializer extends EzyAbstractSerializer<byte[]> {
 
	@Override 
	public byte[] serialize(Object value) {
		return value == null
				? parseNil() 
				: parseNotNull(value);
	}

	@Override
	public ByteBuffer write(Object value) {

		// serialize value to byte array
		byte[] bytes = serialize(value);

		// wrap the byte array
		return ByteBuffer.wrap(bytes);
	}
	 
} 
