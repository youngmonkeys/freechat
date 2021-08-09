package com.tvd12.ezyfoxserver.client.builder;

public interface EzyBuilder<T> {
	
	/**
	 * build project
	 * 
	 * @return the constructed product
	 */
	T build();
	
}
