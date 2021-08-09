package com.tvd12.ezyfoxserver.client.handler;

import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.constant.EzyConstant;
import com.tvd12.ezyfoxserver.client.event.EzyEvent;
import com.tvd12.ezyfoxserver.client.event.EzyEventType;
import com.tvd12.ezyfoxserver.client.logger.EzyLogger;
import com.tvd12.ezyfoxserver.client.socket.EzyPingSchedule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tavandung12 on 9/30/18.
 */

public class EzyEventHandlers extends EzyAbstractHandlers {

    private final Map<EzyConstant, EzyEventHandler> handlers;

    public EzyEventHandlers(EzyClient client, EzyPingSchedule pingSchedule) {
        super(client, pingSchedule);
        this.handlers = new HashMap<>();
    }

    public void addHandler(EzyConstant eventType, EzyEventHandler handler) {
        this.configHandler(handler);
        this.handlers.put(eventType, handler);
    }

    public EzyEventHandler getHandler(EzyConstant eventType) {
        EzyEventHandler handler = handlers.get(eventType);
        return handler;
    }

    public void handle(EzyEvent event) {
        EzyEventType eventType = event.getType();
        EzyEventHandler handler = handlers.get(eventType);
        if(handler != null)
            handler.handle(event);
        else
            EzyLogger.warn("has no handler for event type: " + eventType);
    }
}
