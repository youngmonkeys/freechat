package com.tvd12.ezyfoxserver.client.socket;

import com.tvd12.ezyfoxserver.client.codec.EzyObjectToByteEncoder;

public class EzySimpleSocketDataEncoder implements EzySocketDataEncoder {

	private EzyObjectToByteEncoder encoder;
	
	public EzySimpleSocketDataEncoder(Object encoder) {
		this.encoder = (EzyObjectToByteEncoder)encoder;
	}
	
	@Override
	public byte[] encode(Object data) throws Exception {
		byte[] bytes = encoder.encode(data);
		return bytes;
	}

}
