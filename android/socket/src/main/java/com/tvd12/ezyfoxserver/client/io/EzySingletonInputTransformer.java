package com.tvd12.ezyfoxserver.client.io;

public class EzySingletonInputTransformer extends EzySimpleInputTransformer {
	private static final long serialVersionUID = 3876897322379886846L;
	
	private static final EzySingletonInputTransformer INSTANCE = new EzySingletonInputTransformer();
	
	private EzySingletonInputTransformer() {
	}
	
	public static EzySingletonInputTransformer getInstance() {
		return INSTANCE;
	}
	
}
