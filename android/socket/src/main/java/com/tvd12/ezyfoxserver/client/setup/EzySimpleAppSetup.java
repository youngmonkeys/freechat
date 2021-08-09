package com.tvd12.ezyfoxserver.client.setup;

import com.tvd12.ezyfoxserver.client.handler.EzyAppDataHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyAppDataHandlers;

/**
 * Created by tavandung12 on 10/3/18.
 */

public class EzySimpleAppSetup implements EzyAppSetup {

    private final EzyAppDataHandlers dataHandlers;
    private final EzySetup parent;

    public EzySimpleAppSetup(EzyAppDataHandlers dataHandlers, EzySetup parent) {
        this.parent = parent;
        this.dataHandlers = dataHandlers;
    }

    @Override
    public EzyAppSetup addDataHandler(Object cmd, EzyAppDataHandler dataHandler) {
        dataHandlers.addHandler(cmd, dataHandler);
        return this;
    }

    @Override
    public EzySetup done() {
        return parent;
    }
}
