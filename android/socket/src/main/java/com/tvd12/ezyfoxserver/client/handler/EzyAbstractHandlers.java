package com.tvd12.ezyfoxserver.client.handler;

import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyClientAware;
import com.tvd12.ezyfoxserver.client.socket.EzyPingSchedule;
import com.tvd12.ezyfoxserver.client.socket.EzyPingScheduleAware;

/**
 * Created by tavandung12 on 10/5/18.
 */

public class EzyAbstractHandlers {

    private final EzyClient client;
    private final EzyPingSchedule pingSchedule;

    public EzyAbstractHandlers(EzyClient client, EzyPingSchedule pingSchedule) {
        this.client = client;
        this.pingSchedule = pingSchedule;
    }

    protected void configHandler(Object handler) {
        if(handler instanceof EzyClientAware)
            ((EzyClientAware)handler).setClient(client);
        if(handler instanceof EzyPingScheduleAware)
            ((EzyPingScheduleAware)handler).setPingSchedule(pingSchedule);
    }

}
