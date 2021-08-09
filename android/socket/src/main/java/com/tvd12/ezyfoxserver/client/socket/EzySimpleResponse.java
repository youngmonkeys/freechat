package com.tvd12.ezyfoxserver.client.socket;


import com.tvd12.ezyfoxserver.client.constant.EzyCommand;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;

public class EzySimpleResponse implements EzyResponse {

    private EzyArray data;
    private long timestamp;
    private EzyCommand command;
    
    public EzySimpleResponse(EzyArray data) {
        this.data = data;
        int cmdId = data.get(0, int.class);
        this.command = EzyCommand.valueOf(cmdId);
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public EzyArray getData() {
        return data;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public EzyCommand getCommand() {
        return command;
    }

    @Override
    public void release() {
        this.data = null;
        this.command = null;
    }

}
