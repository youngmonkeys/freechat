package com.tvd12.ezyfoxserver.client.handler;

import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyClientAware;
import com.tvd12.ezyfoxserver.client.event.EzyEvent;

/**
 * Created by tavandung12 on 10/1/18.
 */

public abstract class EzyAbstractEventHandler<E extends EzyEvent>
        implements EzyEventHandler<E>, EzyClientAware {

    protected EzyClient client;

    @Override
    public void setClient(EzyClient client) {
        this.client = client;
    }
}
