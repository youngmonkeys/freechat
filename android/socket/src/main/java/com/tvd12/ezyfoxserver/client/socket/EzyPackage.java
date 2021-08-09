package com.tvd12.ezyfoxserver.client.socket;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.util.EzyReleasable;

public interface EzyPackage extends EzyReleasable {

    EzyArray getData();

    boolean isEncrypted();

    byte[] getEncryptionKey();
    
    EzyConstant getTransportType();
    
}
