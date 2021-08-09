package com.tvd12.ezyfoxserver.client.request;

import com.tvd12.ezyfoxserver.client.constant.EzyCommand;
import com.tvd12.ezyfoxserver.client.entity.EzyData;
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory;

/**
 * Created by tavandung12 on 10/3/18.
 */
public class EzyAppAccessRequest implements EzyRequest {

    protected final String appName;
    protected final EzyData data;

    public EzyAppAccessRequest(String appName) {
        this(appName, null);
    }

    public EzyAppAccessRequest(String appName, EzyData data) {
        this.appName = appName;
        this.data = data;
    }

    @Override
    public EzyData serialize() {
        EzyData answer = EzyEntityFactory.newArrayBuilder()
                .append(appName)
                .append(data)
                .build();
        return answer;
    }

    @Override
    public Object getCommand() {
        return EzyCommand.APP_ACCESS;
    }
}
