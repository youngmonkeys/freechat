package com.tvd12.ezyfoxserver.client.codec;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;

public interface EzyCodecFactory {

    Object newEncoder(EzyConstant connectionType);
    
	Object newDecoder(EzyConstant connectionType);
	
}
