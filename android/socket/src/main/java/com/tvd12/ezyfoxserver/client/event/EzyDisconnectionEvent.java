package com.tvd12.ezyfoxserver.client.event;

/**
 * Created by tavandung12 on 10/5/18.
 */

public class EzyDisconnectionEvent implements EzyEvent {

    private final int reason;

    public EzyDisconnectionEvent(int reason) {
        this.reason = reason;
    }

    public int getReason() {
        return reason;
    }

    @Override
    public EzyEventType getType() {
        return EzyEventType.DISCONNECTION;
    }
}
