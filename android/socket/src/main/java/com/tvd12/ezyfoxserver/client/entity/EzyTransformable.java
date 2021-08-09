package com.tvd12.ezyfoxserver.client.entity;

import com.tvd12.ezyfoxserver.client.io.EzyInputTransformer;
import com.tvd12.ezyfoxserver.client.io.EzyOutputTransformer;

public class EzyTransformable {

	protected final EzyInputTransformer inputTransformer;
	protected final EzyOutputTransformer outputTransformer;
	
	public EzyTransformable(
			EzyInputTransformer inputTransformer, 
			EzyOutputTransformer outputTransformer) {
		this.inputTransformer = inputTransformer;
		this.outputTransformer = outputTransformer;
	}
	
}
