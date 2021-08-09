package com.tvd12.ezyfoxserver.client.util;

/**
 * Created by tavandung12 on 9/20/18.
 */

public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(String str) {
        if(str == null) return true;
        if(str.isEmpty()) return true;
        return false;
    }

}
