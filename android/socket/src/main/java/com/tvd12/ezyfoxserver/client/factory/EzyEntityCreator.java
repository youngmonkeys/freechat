package com.tvd12.ezyfoxserver.client.factory;

import com.tvd12.ezyfoxserver.client.builder.EzyArrayBuilder;
import com.tvd12.ezyfoxserver.client.builder.EzyObjectBuilder;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;

public interface EzyEntityCreator {

	/**
	 * create a product
	 * 
	 * @param <T> the clazz type
	 * @param productType the product type
	 * @return the created product
	 */
	<T> T create(Class<T> productType);
	
	EzyObject newObject();
	
	EzyArray newArray();
	
	EzyObjectBuilder newObjectBuilder();
	
	EzyArrayBuilder newArrayBuilder();
	
}
