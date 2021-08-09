package com.tvd12.ezyfoxserver.client.util;

import com.tvd12.ezyfoxserver.client.builder.EzyArrayBuilder;
import com.tvd12.ezyfoxserver.client.builder.EzyObjectBuilder;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory;

public class EzyEntityBuilders {
	
	protected EzyArray newArray() {
		return EzyEntityFactory.newArray();
	}

	protected EzyObject newObject() {
		return EzyEntityFactory.newObject();
	}
	
	protected EzyArrayBuilder newArrayBuilder() {
		return EzyEntityFactory.newArrayBuilder();
	}

	protected EzyObjectBuilder newObjectBuilder() {
		return EzyEntityFactory.newObjectBuilder();
	}
	
}
