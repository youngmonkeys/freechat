package com.tvd12.ezyfoxserver.client.event;

/**
 * Created by tavandung12 on 10/11/18.
 */

public class EzyTryConnectEvent implements EzyEvent {

    private final int count;

    public EzyTryConnectEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public EzyEventType getType() {
        return EzyEventType.TRY_CONNECT;
    }
}
