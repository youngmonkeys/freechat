package com.tvd12.ezyfoxserver.client.io;

public class EzySingletonOutputTransformer extends EzySimpleOutputTransformer {
	private static final long serialVersionUID = 6000226248827140072L;
	
	private static final EzySingletonOutputTransformer INSTANCE = new EzySingletonOutputTransformer();
	
	private EzySingletonOutputTransformer() {
	}
	
	public static EzySingletonOutputTransformer getInstance() {
		return INSTANCE;
	}
	
}
