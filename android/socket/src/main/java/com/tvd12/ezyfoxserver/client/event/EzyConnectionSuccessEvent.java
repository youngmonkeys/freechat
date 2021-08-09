package com.tvd12.ezyfoxserver.client.event;

/**
 * Created by tavandung12 on 9/30/18.
 */

public class EzyConnectionSuccessEvent implements EzyEvent {
    @Override
    public EzyEventType getType() {
        return EzyEventType.CONNECTION_SUCCESS;
    }
}
