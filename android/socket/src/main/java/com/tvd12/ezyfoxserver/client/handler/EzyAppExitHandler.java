package com.tvd12.ezyfoxserver.client.handler;

import com.tvd12.ezyfoxserver.client.entity.EzyApp;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyZone;
import com.tvd12.ezyfoxserver.client.logger.EzyLogger;
import com.tvd12.ezyfoxserver.client.manager.EzyAppManager;

/**
 * Created by tavandung12 on 10/2/18.
 */

public class EzyAppExitHandler extends EzyAbstractDataHandler {

    @Override
    public void handle(EzyArray data) {
        EzyZone zone = client.getZone();
        EzyAppManager appManager = zone.getAppManager();
        int appId = data.get(0, int.class);
        int reasonId = data.get(1, int.class);
        EzyApp app = appManager.removeApp(appId);
        EzyLogger.info("user exit app: " + app + " reason: " + reasonId);
        postHandle(app, data);
    }

    protected void postHandle(EzyApp app, EzyArray data) {}

}