package com.tvd12.ezyfoxserver.client.event;

/**
 * Created by tavandung12 on 10/11/18.
 */

public class EzyLostPingEvent implements EzyEvent {

    private final int count;

    public EzyLostPingEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public EzyEventType getType() {
        return EzyEventType.LOST_PING;
    }
}
