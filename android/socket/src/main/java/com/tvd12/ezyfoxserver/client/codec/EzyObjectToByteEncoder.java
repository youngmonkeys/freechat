package com.tvd12.ezyfoxserver.client.codec;

public interface EzyObjectToByteEncoder {

	byte[] encode(Object msg) throws Exception;

	byte[] toMessageContent(Object data) throws Exception;

	byte[] encryptMessageContent(byte[] messageContent, byte[] encryptionKey) throws Exception;

}
