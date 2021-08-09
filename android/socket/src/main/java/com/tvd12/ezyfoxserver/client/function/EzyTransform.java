package com.tvd12.ezyfoxserver.client.function;

public interface EzyTransform<I, O> {

	O transform(I input);
	
}
