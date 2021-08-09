package com.tvd12.ezyfoxserver.client.socket;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;
import com.tvd12.ezyfoxserver.client.util.EzyReleasable;

public interface EzyPacket extends EzyReleasable {

	Object getData();
	
	boolean isReleased();
	
	boolean isFragmented();
	
	void setFragment(Object fragment);
	
	EzyConstant getTransportType();
	
	int getSize();
	
}
