package com.tvd12.ezyfoxserver.client.codec;

import java.nio.ByteBuffer;
import java.util.Queue;

import static com.tvd12.ezyfoxserver.client.codec.EzyDecodeState.PREPARE_MESSAGE;
import static com.tvd12.ezyfoxserver.client.codec.EzyDecodeState.READ_MESSAGE_CONTENT;
import static com.tvd12.ezyfoxserver.client.codec.EzyDecodeState.READ_MESSAGE_HEADER;
import static com.tvd12.ezyfoxserver.client.codec.EzyDecodeState.READ_MESSAGE_SIZE;

public class MsgPackByteToObjectDecoder implements EzyByteToObjectDecoder {

	protected final EzyDefaultDecodeHandlers handlers;
	protected final EzyMessageDeserializer deserializer;

	public MsgPackByteToObjectDecoder(
			EzyMessageDeserializer deserializer, int maxSize) {
		this.deserializer = deserializer;
		this.handlers = EzyDefaultDecodeHandlers.builder()
				.maxSize(maxSize)
				.build();
	}

	@Override
	public Object decode(EzyMessage message) throws Exception {
		return deserializer.deserialize(message.getContent());
	}

	@Override
	public Object decode(EzyMessage message, byte[] decryptionKey) throws Exception {
		byte[] encryptedContent = message.getContent();
		if(message.getHeader().isEncrypted())
			encryptedContent = decryptMessageContent(encryptedContent, decryptionKey);
		return deserializer.deserialize(encryptedContent);
	}

	protected byte[] decryptMessageContent(
			byte[] content, byte[] decryptionKey) throws Exception {
		return content;
	}

	@Override
	public void decode(ByteBuffer bytes, Queue<EzyMessage> out) throws Exception {
		handlers.handle(bytes, out);
	}

}

abstract class AbstractDecodeHandler implements EzyDecodeHandler {

	protected EzyDecodeHandler nextHandler;
	protected EzyByteBufferMessageReader messageReader;

	public void setNextHandler(EzyDecodeHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public void setMessageReader(EzyByteBufferMessageReader messageReader) {
		this.messageReader = messageReader;
	}

	@Override
	public EzyDecodeHandler nextHandler() {
		return nextHandler;
	}
}

class PrepareMessage extends AbstractDecodeHandler {
	
	@Override
	public EzyIDecodeState nextState() {
		return READ_MESSAGE_HEADER;
	}
	
	@Override
	public boolean handle(ByteBuffer in, Queue<EzyMessage> out) {
		messageReader.clear();
		return true;
	}
}

class ReadMessageHeader extends AbstractDecodeHandler {

	@Override
	public EzyIDecodeState nextState() {
		return READ_MESSAGE_SIZE;
	}

	@Override
	public boolean handle(ByteBuffer in, Queue<EzyMessage> out) {
		return messageReader.readHeader(in);
	}
	
}

class ReadMessageSize extends AbstractDecodeHandler {

	protected final int maxSize;
	
	public ReadMessageSize(int maxSize) {
		this.maxSize = maxSize;
	}
	
	@Override
	public EzyIDecodeState nextState() {
		return READ_MESSAGE_CONTENT;
	}

	@Override
	public boolean handle(ByteBuffer in, Queue<EzyMessage> out) {
		return messageReader.readSize(in, maxSize);
	}
}

class ReadMessageContent extends AbstractDecodeHandler {
	
	@Override
	public EzyIDecodeState nextState() {
		return PREPARE_MESSAGE;
	}

	@Override
	public boolean handle(ByteBuffer in, Queue<EzyMessage> out) {
		if(!messageReader.readContent(in))
			return false;
		out.add(messageReader.get());
		return true;
	}
	
}
