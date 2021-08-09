package com.tvd12.ezyfoxserver.client.event;

import com.tvd12.ezyfoxserver.client.constant.EzyConnectionFailedReason;
import com.tvd12.ezyfoxserver.client.constant.EzyConstant;

/**
 * Created by tavandung12 on 9/30/18.
 */

public class EzyConnectionFailureEvent implements EzyEvent {

    private final EzyConstant reason;

    public EzyConnectionFailureEvent(EzyConstant reason) {
        this.reason = reason;
    }

    public static EzyConnectionFailureEvent networkUnreachable() {
        return new EzyConnectionFailureEvent(EzyConnectionFailedReason.NETWORK_UNREACHABLE);
    }

    public static EzyConnectionFailureEvent unknownHost() {
        return new EzyConnectionFailureEvent(EzyConnectionFailedReason.UNKNOWN_HOST);
    }

    public static EzyConnectionFailureEvent connectionRefused() {
        return new EzyConnectionFailureEvent(EzyConnectionFailedReason.CONNECTION_REFUSED);
    }

    public static EzyConnectionFailureEvent unknown() {
        return new EzyConnectionFailureEvent(EzyConnectionFailedReason.UNKNOWN);
    }

    public EzyConstant getReason() {
        return reason;
    }

    @Override
    public EzyEventType getType() {
        return EzyEventType.CONNECTION_FAILURE;
    }
}
