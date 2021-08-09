package com.tvd12.ezyfoxserver.client.socket;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;
import com.tvd12.ezyfoxserver.client.constant.EzyTransportType;

public class EzySimplePacket implements EzyPacket {

	private Object data;
	private boolean released;
	private boolean fragmented;
	private EzyConstant transportType = EzyTransportType.TCP;

	public void setData(Object data) {
		this.data = data;
	}

	public void setTransportType(EzyConstant transportType) {
		this.transportType = transportType;
	}

	@Override
	public void setFragment(Object fragment) {
	    this.data = fragment;
	    this.fragmented = true;
	}
	
	@Override
	public int getSize() {
	    if(data instanceof String)
	        return ((String)data).length();
	    return ((byte[])data).length;
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public boolean isReleased() {
		return released;
	}

	@Override
	public boolean isFragmented() {
		return fragmented;
	}

	@Override
	public EzyConstant getTransportType() {
		return transportType;
	}

	@Override
	public void release() {
	    this.data = null;
	    this.released = true;
	}
	
	@Override
	public String toString() {
	    return new StringBuilder()
	            .append("(")
	            .append("transportType: ")
	                .append(transportType)
	            .append(", data: ")
	                .append(data)
	            .append(")")
	            .toString();
	}
}
