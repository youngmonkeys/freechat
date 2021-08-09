package com.tvd12.ezyfoxserver.client.codec;

import java.nio.ByteBuffer;
import java.util.Queue;

public interface EzyByteToObjectDecoder {

	Object decode(EzyMessage message) throws Exception;
	
	void decode(ByteBuffer bytes, Queue<EzyMessage> queue) throws Exception;

	Object decode(EzyMessage message, byte[] encryptionKey) throws Exception;

}
