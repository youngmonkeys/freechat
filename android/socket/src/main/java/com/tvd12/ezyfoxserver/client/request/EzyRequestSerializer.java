package com.tvd12.ezyfoxserver.client.request;

import com.tvd12.ezyfoxserver.client.constant.EzyCommand;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;

public interface EzyRequestSerializer {
    EzyArray serialize(EzyCommand cmd, EzyArray data);
}

