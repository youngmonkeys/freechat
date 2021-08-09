package com.tvd12.ezyfoxserver.client.codec;

import java.nio.ByteBuffer;

public interface EzyObjectSerializer {

	/**
	 * serialize a value to byte array
	 *
	 * @param value the value
	 * @return the byte array
	 */
	byte[] serialize(Object value);

	/**
	 * serialize a value to byte buffer
	 *
	 * @param value the value
	 * @return the byte buffer
	 */
	ByteBuffer write(Object value);

}
