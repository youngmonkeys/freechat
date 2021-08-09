package com.tvd12.ezyfoxserver.client.setup;

import com.tvd12.ezyfoxserver.client.event.EzyEventType;
import com.tvd12.ezyfoxserver.client.handler.EzyDataHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyEventHandler;

/**
 * Created by tavandung12 on 9/30/18.
 */

public interface EzySetup {

    EzySetup addDataHandler(Object cmd, EzyDataHandler dataHandler);

    EzySetup addEventHandler(EzyEventType eventType, EzyEventHandler eventHandler);

    EzyAppSetup setupApp(String appName);

}
