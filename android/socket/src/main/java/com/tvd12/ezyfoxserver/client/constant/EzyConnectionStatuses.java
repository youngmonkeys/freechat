package com.tvd12.ezyfoxserver.client.constant;

public final class EzyConnectionStatuses {

    private EzyConnectionStatuses() {
    }

    public static boolean isClientConnectable(EzyConnectionStatus status) {
        return status == EzyConnectionStatus.NULL ||
                status == EzyConnectionStatus.DISCONNECTED ||
                status == EzyConnectionStatus.FAILURE;
    }

    public static boolean isClientReconnectable(EzyConnectionStatus status) {
        return status == EzyConnectionStatus.DISCONNECTED ||
                status == EzyConnectionStatus.FAILURE;
    }

}