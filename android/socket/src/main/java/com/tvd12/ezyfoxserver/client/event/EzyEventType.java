package com.tvd12.ezyfoxserver.client.event;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;

/**
 * Created by tavandung12 on 9/30/18.
 */

public enum EzyEventType implements EzyConstant {

    CONNECTION_SUCCESS(1),
    CONNECTION_FAILURE(2),
    DISCONNECTION(3),
    LOST_PING(4),
    TRY_CONNECT(5);

    private final int id;

    private EzyEventType(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return toString();
    }
}
