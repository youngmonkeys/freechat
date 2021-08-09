package com.tvd12.ezyfoxserver.client.config;

import com.tvd12.ezyfoxserver.client.builder.EzyBuilder;

/**
 * Created by tavandung12 on 10/6/18.
 */

public class EzyReconnectConfig {

    private final boolean enable;
    private final int maxReconnectCount;
    private final int reconnectPeriod;

    protected EzyReconnectConfig(Builder builder) {
        this.enable = builder.enable;
        this.reconnectPeriod = builder.reconnectPeriod;
        this.maxReconnectCount = builder.maxReconnectCount;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public int getMaxReconnectCount() {
        return maxReconnectCount;
    }

    public int getReconnectPeriod() {
        return reconnectPeriod;
    }

    public static class Builder implements EzyBuilder<EzyReconnectConfig> {
        private boolean enable = true;
        private int maxReconnectCount = 5;
        private int reconnectPeriod = 3000;
        private EzyClientConfig.Builder parent;

        public Builder(EzyClientConfig.Builder parent) {
            this.parent = parent;
        }

        public Builder enable(boolean enable) {
            this.enable = enable;
            return this;
        }

        public Builder reconnectPeriod(int reconnectPeriod) {
            this.reconnectPeriod = reconnectPeriod;
            return this;
        }

        public Builder maxReconnectCount(int maxReconnectCount) {
            this.maxReconnectCount = maxReconnectCount;
            return this;
        }

        public EzyClientConfig.Builder done() {
            return this.parent;
        }

        @Override
        public EzyReconnectConfig build() {
            return new EzyReconnectConfig(this);
        }
    }
}
