package com.tvd12.ezyfoxserver.client.builder;

import com.tvd12.ezyfoxserver.client.entity.EzyHashMap;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;
import com.tvd12.ezyfoxserver.client.entity.EzyTransformable;
import com.tvd12.ezyfoxserver.client.io.EzyInputTransformer;
import com.tvd12.ezyfoxserver.client.io.EzyOutputTransformer;

import java.util.Map;

public class EzySimpleObjectBuilder 
		extends EzyTransformable
		implements EzyObjectBuilder {

	protected final EzyObject product;
	
	public EzySimpleObjectBuilder(
			EzyInputTransformer inputTransformer,
			EzyOutputTransformer outputTransformer) {
		super(inputTransformer, outputTransformer);
		this.product = newProduct();
	}
	
	protected EzyHashMap newProduct() {
		EzyHashMap answer = new EzyHashMap(inputTransformer, outputTransformer);
		return answer;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.builder.EzyObjectBuilder#append(java.lang.Object, java.lang.Object)
	 */
	@Override
	public EzyObjectBuilder append(Object key, Object value) {
		this.product.put(key, value);
		return this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.builder.EzyObjectBuilder#append(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public EzyObjectBuilder append(Map map) {
		this.product.putAll(map);
		return this;
	}

	@Override
	public EzyObjectBuilder append(Object key, EzyBuilder builder) {
		Object value = builder.build();
		return append(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.builder.EzyBuilder#build()
	 */
	@Override
	public EzyObject build() {
		return this.product;
	}
	
}
