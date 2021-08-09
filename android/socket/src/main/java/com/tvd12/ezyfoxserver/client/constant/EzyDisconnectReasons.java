package com.tvd12.ezyfoxserver.client.constant;

import java.util.HashMap;
import java.util.Map;

public final class EzyDisconnectReasons {

    private static final Map<Integer, String> REASON_NAMES = newReasonNames();

    private EzyDisconnectReasons() {
    }

    public static String getDisconnectReasonName(int reason)
    {
        String reasonName = REASON_NAMES.get(reason);
        if(reasonName != null)
            return reasonName;
        return String.valueOf(reason);
    }

    private static Map<Integer, String> newReasonNames()
    {
        Map<Integer, String> map = new HashMap<>();
        map.put(EzyDisconnectReason.UNKNOWN.getId(), "UNKNOWN");
        map.put(EzyDisconnectReason.IDLE.getId(), "IDLE");
        map.put(EzyDisconnectReason.NOT_LOGGED_IN.getId(), "NOT_LOGGED_IN");
        map.put(EzyDisconnectReason.ANOTHER_SESSION_LOGIN.getId(),"ANOTHER_SESSION_LOGIN");
        map.put(EzyDisconnectReason.ADMIN_BAN.getId(),"ADMIN_BAN");
        map.put(EzyDisconnectReason.ADMIN_KICK.getId(),"ADMIN_KICK");
        map.put(EzyDisconnectReason.MAX_REQUEST_PER_SECOND.getId(),"MAX_REQUEST_PER_SECOND");
        map.put(EzyDisconnectReason.MAX_REQUEST_SIZE.getId(),"MAX_REQUEST_SIZE");
        map.put(EzyDisconnectReason.SERVER_ERROR.getId(),"SERVER_ERROR");
        map.put(EzyDisconnectReason.SERVER_NOT_RESPONDING.getId(),"SERVER_NOT_RESPONDING");
        return map;
    }
}
