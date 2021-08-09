package com.tvd12.ezyfoxserver.client.constant;

public final class EzySocketStatuses {

    private EzySocketStatuses() {
    }

    public static boolean isSocketConnectable(EzySocketStatus status) {
        return status == EzySocketStatus.NOT_CONNECT ||
                status == EzySocketStatus.DISCONNECTED ||
                status == EzySocketStatus.CONNECT_FAILED;
    }

    public static boolean isSocketDisconnectable(EzySocketStatus status) {
        return status == EzySocketStatus.CONNECTED ||
                status == EzySocketStatus.DISCONNECTING;
    }

    public static boolean isSocketReconnectable(EzySocketStatus status) {
        return status == EzySocketStatus.DISCONNECTED ||
                status == EzySocketStatus.CONNECT_FAILED;
    }
}
