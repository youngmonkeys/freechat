package com.tvd12.ezyfoxserver.client.request;

import com.tvd12.ezyfoxserver.client.constant.EzyCommand;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory;

public class EzySimpleRequestSerializer implements EzyRequestSerializer {

    @Override
    public EzyArray serialize(EzyCommand cmd, EzyArray data) {
        EzyArray array = EzyEntityFactory.newArrayBuilder()
                .append(cmd.getId())
                .append(data)
                .build();
        return array;
    }
}
