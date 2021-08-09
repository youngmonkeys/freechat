package com.tvd12.ezyfoxserver.client.constant;

public enum EzySocketStatus implements EzyConstant {

    NOT_CONNECT(1),
    CONNECTING(2),
    CONNECTED(3),
    CONNECT_FAILED(4),
    DISCONNECTING(5),
    DISCONNECTED(6),
    RECONNECTING(7);

    private final int id;

    private EzySocketStatus(int id) {
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
