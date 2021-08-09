package com.tvd12.ezyfoxserver.client.builder;

import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyArrayList;
import com.tvd12.ezyfoxserver.client.entity.EzyTransformable;
import com.tvd12.ezyfoxserver.client.io.EzyCollectionConverter;
import com.tvd12.ezyfoxserver.client.io.EzyInputTransformer;
import com.tvd12.ezyfoxserver.client.io.EzyOutputTransformer;

public class EzySimpleArrayBuilder
		extends EzyTransformable
		implements EzyArrayBuilder {

	protected final EzyArray product;
	protected final EzyCollectionConverter collectionConverter;
	
	public EzySimpleArrayBuilder(
			EzyInputTransformer inputTransformer,
			EzyOutputTransformer outputTransformer,
			EzyCollectionConverter collectionConverter) {
		super(inputTransformer, outputTransformer);
		this.collectionConverter = collectionConverter;
		this.product = newProduct();
	}
	
	protected EzyArray newProduct() {
		EzyArrayList answer = new EzyArrayList(
				inputTransformer, 
				outputTransformer, collectionConverter);
		return answer;
	}
	
	@Override
	public <T> EzyArrayBuilder append(T item) {
		this.product.add(item);
		return this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.builder.EzyArrayBuilder#append(java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> EzyArrayBuilder append(T... items) {
		this.product.add(items);
		return this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.builder.EzyArrayBuilder#append(java.util.Collection)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public EzyArrayBuilder append(Iterable items) {
		this.product.add(items);
		return this;
	}

	@Override
	public EzyArrayBuilder append(EzyBuilder builder) {
		Object value = builder.build();
		return append(value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.builder.EzyBuilder#build()
	 */
	@Override
	public EzyArray build() {
		return product;
	}

}
