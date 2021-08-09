package com.tvd12.ezyfoxserver.client.codec;

public class MsgPackObjectToMessage implements EzyObjectToMessage {

	private final EzyObjectToBytes objectToBytes;
	
	public MsgPackObjectToMessage() {
		this.objectToBytes = new MsgPackObjectToBytes(newSerializer());
	}

	protected EzyMessageSerializer newSerializer() {
		EzyMessageSerializer serializer = new MsgPackSimpleSerializer();
		return serializer;
	}

	@Override
	public EzyMessage convert(Object object) {
		EzyMessage message = packToMessage(convertToMessageContent(object), false);
		return message;
	}

	@Override
	public byte[] convertToMessageContent(Object object) {
		return objectToBytes.convert(object);
	}

	@Override
	public EzyMessage packToMessage(byte[] content, boolean encrypted) {
		EzyMessage message = new EzySimpleMessage(newHeader(content, encrypted), content, content.length);
		return message;
	}

	private EzyMessageHeader newHeader(byte[] content, boolean encrypted) {
		EzyMessageHeader header = new EzySimpleMessageHeader(isBigMessage(content), encrypted, false, false, false, false);
		return header;
	}

	private boolean isBigMessage(byte[] content) {
		boolean answer = content.length > MsgPackConstant.MAX_SMALL_MESSAGE_SIZE;
		return answer;
	}
}
