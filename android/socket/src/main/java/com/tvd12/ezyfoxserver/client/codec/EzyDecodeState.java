package com.tvd12.ezyfoxserver.client.codec;

public enum EzyDecodeState implements EzyIDecodeState {

	PREPARE_MESSAGE(0),
	READ_MESSAGE_HEADER(1),
	READ_MESSAGE_SIZE(2),
	READ_MESSAGE_CONTENT(3);

	private final int id;
	
	private EzyDecodeState(final int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}
}
