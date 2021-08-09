package com.tvd12.ezyfoxserver.client.socket;


import com.tvd12.ezyfoxserver.client.constant.EzyCommand;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.util.EzyReleasable;

public interface EzyResponse extends EzyReleasable {

    EzyArray getData();

    long getTimestamp();
    
    EzyCommand getCommand();
    
}
