package com.tvd12.ezyfoxserver.client.codec;

import com.tvd12.ezyfoxserver.client.io.EzyBytes;
import com.tvd12.ezyfoxserver.client.io.EzyInts;

import java.nio.ByteBuffer;

public class EzyByteBufferMessageReader extends EzyMessageReader<ByteBuffer> {

	@Override
	protected byte readByte(ByteBuffer buffer) {
		return buffer.get();
	}
	
	@Override
	protected int remaining(ByteBuffer buffer) {
		return buffer.remaining();
	}
	
	@Override
	protected int readMessgeSize(ByteBuffer buffer) {
		return EzyInts.bin2uint(EzyBytes.copy(buffer, getSizeLength()));
	}
	
	@Override
	protected void readMessageContent(ByteBuffer buffer, byte[] content) {
		buffer.get(content);
	}

}
