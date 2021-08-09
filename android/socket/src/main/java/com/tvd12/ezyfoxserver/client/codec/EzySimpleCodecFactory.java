package com.tvd12.ezyfoxserver.client.codec;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;

public class EzySimpleCodecFactory implements EzyCodecFactory {

	private final EzyCodecCreator socketCodecCreator;

	public EzySimpleCodecFactory() {
		this(false);
	}

	public EzySimpleCodecFactory(boolean enableSSL) {
		this.socketCodecCreator = new MsgPackCodecCreator(enableSSL);
	}

	@Override
	public Object newEncoder(EzyConstant connectionType) {
		Object encoder = socketCodecCreator.newEncoder();
		return encoder;
	}

	@Override
	public Object newDecoder(EzyConstant connectionType) {
		Object decoder = socketCodecCreator.newDecoder(Integer.MAX_VALUE);
		return decoder;
	}
	
}
