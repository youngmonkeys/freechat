package com.tvd12.ezyfoxserver.client.codec;

public class MsgPackCodecCreator implements EzyCodecCreator {

	protected final boolean enableEncryption;
	protected final EzyMessageToBytes messageToBytes;
	protected final EzyObjectToMessage objectToMessage;
	protected final EzyMessageDeserializer deserializer;

	public MsgPackCodecCreator() {
		this(false);
	}

	public MsgPackCodecCreator(boolean enableEncryption) {
		this.enableEncryption = enableEncryption;
		this.messageToBytes = new EzySimpleMessageToBytes();
		this.objectToMessage = new MsgPackObjectToMessage();
		this.deserializer = new MsgPackSimpleDeserializer();
	}

	@Override
	public EzyByteToObjectDecoder newDecoder(int maxRequestSize) {
		return enableEncryption
				? new MsgPackAesByteToObjectDecoder(deserializer, maxRequestSize)
				: new MsgPackByteToObjectDecoder(deserializer, maxRequestSize);
	}

	@Override
	public EzyObjectToByteEncoder newEncoder() {
		return enableEncryption
				? new MsgPackAesObjectToByteEncoder(messageToBytes, objectToMessage)
				: new MsgPackObjectToByteEncoder(messageToBytes, objectToMessage);
	}

}
