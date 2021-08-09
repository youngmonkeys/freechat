package com.tvd12.ezyfoxserver.client.constant;

/**
 * Created by tavandung12 on 9/30/18.
 */

public enum EzyConnectionFailedReason implements EzyConstant {

    NETWORK_UNREACHABLE(1),
    UNKNOWN_HOST(2),
    CONNECTION_REFUSED(3),
    UNKNOWN(4);

    private final int id;

    private EzyConnectionFailedReason(int id) {
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
