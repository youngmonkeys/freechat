package com.tvd12.ezyfoxserver.client.socket;

import com.tvd12.ezyfoxserver.client.entity.EzyArray;

public class EzySocketResponseApi extends EzyAbstractResponseApi {

	protected final EzySocketDataEncoder encoder;

	public EzySocketResponseApi(EzySocketDataEncoder encoder, EzyPacketQueue packetQueue) {
		super(packetQueue);
		this.encoder = encoder;
	}

	@Override
	protected Object encodeData(EzyArray data) throws Exception {
		Object answer = encoder.encode(data);
		return answer;
	}
	
}
