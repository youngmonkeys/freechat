package com.tvd12.freechat.app.util;

import com.tvd12.ezyfoxserver.entity.EzyUser;

import static com.tvd12.freechat.common.constant.ChatConstants.PROPERTY_DATA_ID;

public final class EzyUsers {

    private EzyUsers() {}

    public static long getDbUserId(EzyUser user) {
        return user.getProperty(PROPERTY_DATA_ID, Long.class);
    }
}
