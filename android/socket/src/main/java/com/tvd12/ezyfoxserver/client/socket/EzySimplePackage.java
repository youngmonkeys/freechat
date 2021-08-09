package com.tvd12.ezyfoxserver.client.socket;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;
import com.tvd12.ezyfoxserver.client.constant.EzyTransportType;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;

public class EzySimplePackage implements EzyPackage {

    protected EzyArray data;
    protected boolean encrypted;
    protected byte[] encryptionKey;
    protected EzyConstant transportType;

    public EzySimplePackage(
            EzyArray data,
            boolean encrypted, byte[] encryptionKey) {
        this(data, encrypted, encryptionKey, EzyTransportType.TCP);
    }

    public EzySimplePackage(EzyArray data, EzyConstant transportType) {
        this(data, false, transportType);
    }

    public EzySimplePackage(
            EzyArray data,
            boolean encrypted, EzyConstant transportType) {
        this(data, encrypted, null, transportType);
    }

    public EzySimplePackage(
            EzyArray data,
            boolean encrypted,
            byte[] encryptionKey, EzyConstant transportType) {
        this.data = data;
        this.encrypted = encrypted;
        this.encryptionKey = encryptionKey;
        this.transportType = transportType;
    }

    @Override
    public EzyArray getData() {
        return data;
    }

    @Override
    public boolean isEncrypted() {
        return encrypted;
    }

    @Override
    public byte[] getEncryptionKey() {
        return encryptionKey;
    }

    @Override
    public EzyConstant getTransportType() {
        return transportType;
    }

    @Override
    public void release() {
        this.data = null;
    }

}
