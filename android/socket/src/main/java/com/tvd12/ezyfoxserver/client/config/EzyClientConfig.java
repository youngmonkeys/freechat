package com.tvd12.ezyfoxserver.client.config;

import com.tvd12.ezyfoxserver.client.builder.EzyBuilder;

/**
 * Created by tavandung12 on 10/11/18.
 */

public class EzyClientConfig implements EzySocketClientConfig {

    private final String zoneName;
    private final String clientName;
    private final boolean enableSSL;
    private final boolean enableDebug;
    private final EzyReconnectConfig reconnect;

    protected EzyClientConfig(Builder builder) {
        this.zoneName = builder.zoneName;
        this.clientName = builder.clientName;
        this.enableSSL = builder.enableSSL;
        this.enableDebug = builder.enableDebug;
        this.reconnect = builder.reconnectConfigBuilder.build();
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getClientName() {
        if(clientName == null)
            return zoneName;
        return clientName;
    }

    public EzyReconnectConfig getReconnect() {
        return reconnect;
    }

    @Override
    public boolean isEnableSSL() {
        return enableSSL;
    }

    public boolean isEnableDebug() {
        return enableDebug;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements EzyBuilder<EzyClientConfig> {

        private String zoneName;
        private String clientName;
        private boolean enableSSL;
        private boolean enableDebug;
        private final EzyReconnectConfig.Builder reconnectConfigBuilder;

        public Builder() {
            this.reconnectConfigBuilder = new EzyReconnectConfig.Builder(this);
        }

        public Builder zoneName(String zoneName) {
            this.zoneName = zoneName;
            return this;
        }

        public Builder clientName(String clientName) {
            this.clientName = clientName;
            return this;
        }

        public Builder enableSSL() {
            return enableSSL(true);
        }

        public Builder enableSSL(boolean enableSSL) {
            this.enableSSL = enableSSL;
            return this;
        }

        public Builder enableDebug() {
            return enableDebug(true);
        }

        public Builder enableDebug(boolean enableDebug) {
            this.enableDebug = enableDebug;
            return this;
        }

        public EzyReconnectConfig.Builder reconnectConfigBuilder() {
            return reconnectConfigBuilder;
        }

        @Override
        public EzyClientConfig build() {
            return new EzyClientConfig(this);
        }

    }
}
