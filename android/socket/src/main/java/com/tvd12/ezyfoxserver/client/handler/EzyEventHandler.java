package com.tvd12.ezyfoxserver.client.handler;

import com.tvd12.ezyfoxserver.client.event.EzyEvent;

/**
 * Created by tavandung12 on 9/30/18.
 */

public interface EzyEventHandler<E extends EzyEvent> {

    void handle(E event);

}
