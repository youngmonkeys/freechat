package com.tvd12.ezyfoxserver.client.manager;

/**
 * Created by tavandung12 on 10/8/18.
 */

public interface EzyPingManager {

    long getPingPeriod();

    int increaseLostPingCount();

    int getMaxLostPingCount();

    void setLostPingCount(int count);

}
