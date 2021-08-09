package com.tvd12.ezyfoxserver.client.handler;

import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;
import com.tvd12.ezyfoxserver.client.config.EzyReconnectConfig;
import com.tvd12.ezyfoxserver.client.constant.EzyConnectionStatus;
import com.tvd12.ezyfoxserver.client.event.EzyConnectionFailureEvent;
import com.tvd12.ezyfoxserver.client.logger.EzyLogger;

/**
 * Created by tavandung12 on 10/3/18.
 */

public class EzyConnectionFailureHandler extends EzyAbstractEventHandler<EzyConnectionFailureEvent> {

    @Override
    public final void handle(EzyConnectionFailureEvent event) {
        EzyLogger.info("connection failure, reason = " + event.getReason());
        EzyClientConfig config = client.getConfig();
        EzyReconnectConfig reconnectConfig = config.getReconnect();
        boolean shouldReconnect = shouldReconnect(event);
        boolean mustReconnect = reconnectConfig.isEnable() && shouldReconnect;
        boolean reconnecting = false;
        client.setStatus(EzyConnectionStatus.FAILURE);
        if(mustReconnect)
            reconnecting = client.reconnect();
        if(!reconnecting) {
            control(event);
        }
    }

    protected boolean shouldReconnect(EzyConnectionFailureEvent event) {
        return true;
    }

    protected void control(EzyConnectionFailureEvent event) {
    }
}
