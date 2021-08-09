package com.tvd12.ezyfoxserver.client.socket;

import com.tvd12.ezyfoxserver.client.logger.EzyLogger;

import java.nio.channels.SocketChannel;

public class EzyTcpSocketReader extends EzySocketReader {

    protected SocketChannel socket;

    public void setSocket(SocketChannel socket) {
        this.socket = socket;
    }

    @Override
    protected long readSocketData() {
        try {
            long bytesToRead = socket.read(buffer);
            return bytesToRead;
        }
        catch (Exception e) {
            EzyLogger.warn("I/O error at socket-reader", e);
            return -1;
        }
    }
}
