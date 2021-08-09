package com.tvd12.ezyfoxserver.client.constant;

import java.util.concurrent.atomic.AtomicInteger;

public interface EzyConstant extends EzyHasIntId, EzyHasName {

	// the counter utility
	AtomicInteger COUNTER = new AtomicInteger(0);
	
	/**
	 * Get constant name
	 * 
	 * @return the constant name
	 */
	String getName();
	
}
