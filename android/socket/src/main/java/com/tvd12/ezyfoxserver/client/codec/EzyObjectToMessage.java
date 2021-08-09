package com.tvd12.ezyfoxserver.client.codec;

public interface EzyObjectToMessage {

	EzyMessage convert(Object object);

	byte[] convertToMessageContent(Object object);

	EzyMessage packToMessage(byte[] content, boolean encrypted);

}
