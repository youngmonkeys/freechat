package com.tvd12.ezyfoxserver.client.manager;

import com.tvd12.ezyfoxserver.client.entity.EzyApp;

import java.util.List;

/**
 * Created by tavandung12 on 10/5/18.
 */

public interface EzyAppGroup extends EzyAppByIdGroup {

    EzyApp getApp();

    List<EzyApp> getAppList();

    EzyApp getAppByName(String appName);

}
