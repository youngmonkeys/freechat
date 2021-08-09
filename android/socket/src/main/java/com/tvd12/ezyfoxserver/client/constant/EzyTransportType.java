package com.tvd12.ezyfoxserver.client.constant;

public enum EzyTransportType implements EzyConstant {

	TCP(1),
	UDP(2);

	private final int id;
	
	private EzyTransportType(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return toString();
	}
	
}
