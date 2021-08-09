package com.tvd12.ezyfoxserver.client;

import com.tvd12.ezyfoxserver.client.constant.EzyDisconnectReason;
import com.tvd12.ezyfoxserver.client.setup.EzySetup;
import com.tvd12.ezyfoxserver.client.setup.EzySimpleSetup;
import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;
import com.tvd12.ezyfoxserver.client.constant.EzyCommand;
import com.tvd12.ezyfoxserver.client.constant.EzyConnectionStatus;
import com.tvd12.ezyfoxserver.client.entity.EzyApp;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyData;
import com.tvd12.ezyfoxserver.client.entity.EzyEntity;
import com.tvd12.ezyfoxserver.client.entity.EzyMeAware;
import com.tvd12.ezyfoxserver.client.entity.EzyUser;
import com.tvd12.ezyfoxserver.client.entity.EzyZone;
import com.tvd12.ezyfoxserver.client.entity.EzyZoneAware;
import com.tvd12.ezyfoxserver.client.logger.EzyLogger;
import com.tvd12.ezyfoxserver.client.manager.EzyAppManager;
import com.tvd12.ezyfoxserver.client.manager.EzyHandlerManager;
import com.tvd12.ezyfoxserver.client.manager.EzyPingManager;
import com.tvd12.ezyfoxserver.client.manager.EzySimpleHandlerManager;
import com.tvd12.ezyfoxserver.client.manager.EzySimplePingManager;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;
import com.tvd12.ezyfoxserver.client.request.EzyRequestSerializer;
import com.tvd12.ezyfoxserver.client.request.EzySimpleRequestSerializer;
import com.tvd12.ezyfoxserver.client.socket.EzyPingSchedule;
import com.tvd12.ezyfoxserver.client.socket.EzySocketClient;
import com.tvd12.ezyfoxserver.client.socket.EzyTcpSocketClient;

import java.util.HashSet;
import java.util.Set;

import static com.tvd12.ezyfoxserver.client.constant.EzyConnectionStatuses.isClientConnectable;
import static com.tvd12.ezyfoxserver.client.constant.EzyConnectionStatuses.isClientReconnectable;

/**
 * Created by tavandung12 on 9/20/18.
 */

public class EzyTcpClient
        extends EzyEntity
        implements EzyClient, EzyMeAware, EzyZoneAware {

    protected EzyUser me;
    protected EzyZone zone;
    protected long sessionId;
    protected byte[] publicKey;
    protected byte[] privateKey;
    protected byte[] sessionKey;
    protected String sessionToken;
    protected final String name;
    protected final EzySetup settingUp;
    protected final EzyClientConfig config;
    protected final EzyPingManager pingManager;
    protected final EzyHandlerManager handlerManager;
    protected final EzyRequestSerializer requestSerializer;

    protected EzyConnectionStatus status;
    protected final Set<Object> unloggableCommands;

    protected final EzySocketClient socketClient;
    protected final EzyPingSchedule pingSchedule;

    public EzyTcpClient(EzyClientConfig config) {
        this.config = config;
        this.name = config.getClientName();
        this.status = EzyConnectionStatus.NULL;
        this.pingManager = new EzySimplePingManager();
        this.pingSchedule = new EzyPingSchedule(this);
        this.handlerManager = new EzySimpleHandlerManager(this);
        this.requestSerializer = new EzySimpleRequestSerializer();
        this.settingUp = new EzySimpleSetup(handlerManager);
        this.unloggableCommands = newUnloggableCommands();
        this.socketClient = newSocketClient();
    }

    protected Set<Object> newUnloggableCommands() {
        Set<Object> set = new HashSet<Object>();
        set.add(EzyCommand.PING);
        set.add(EzyCommand.PONG);
        return set;
    }

    protected EzySocketClient newSocketClient() {
        EzyTcpSocketClient client = new EzyTcpSocketClient(config);
        client.setPingSchedule(pingSchedule);
        client.setPingManager(pingManager);
        client.setHandlerManager(handlerManager);
        client.setReconnectConfig(config.getReconnect());
        client.setUnloggableCommands(unloggableCommands);
        return client;
    }

    @Override
    public EzySetup setup() {
        return settingUp;
    }

    @Override
    public void connect(String host, int port) {
        try {
            if (!isClientConnectable(status)) {
                EzyLogger.warn("client has already connected to: " + host + ":" + port);
                return;
            }
            preconnect();
            socketClient.connectTo(host, port);
            setStatus(EzyConnectionStatus.CONNECTING);
        } catch (Exception e) {
            EzyLogger.error("connect to server error", e);
        }
    }

    @Override
    public boolean reconnect() {
        if (!isClientReconnectable(status)) {
            String host = socketClient.getHost();
            int port = socketClient.getPort();
            EzyLogger.warn("client has already connected to: " + host + ":" + port);
            return false;
        }
        preconnect();
        boolean success = socketClient.reconnect();
        if (success)
            setStatus(EzyConnectionStatus.RECONNECTING);
        return success;
    }

    protected void preconnect() {
        this.me = null;
        this.zone = null;
        this.publicKey = null;
        this.privateKey = null;
        this.sessionKey = null;
    }

    @Override
    public void disconnect() {
        disconnect(EzyDisconnectReason.CLOSE.getId());
    }

    @Override
    public void close() {
        disconnect();
    }

    @Override
    public void disconnect(int reason) {
        socketClient.disconnect(reason);
    }

    @Override
    public void send(EzyRequest request) {
        send(request, false);
    }

    @Override
    public void send(EzyRequest request, boolean encrypted) {
        Object cmd = request.getCommand();
        EzyData data = request.serialize();
        send((EzyCommand) cmd, (EzyArray) data, encrypted);
    }

    @Override
    public void send(EzyCommand cmd, EzyArray data) {
        send(cmd, data, false);
    }

    @Override
    public void send(EzyCommand cmd, EzyArray data, boolean encrypted) {
        boolean shouldEncrypted = encrypted;
        if(encrypted && sessionKey == null) {
            if(config.isEnableDebug()) {
                shouldEncrypted = false;
            }
            else {
                throw new IllegalArgumentException(
                        "can not send command: " + cmd + " " +
                                "you must enable SSL or enable debug mode by configuration " +
                                "when you create the client"
                );
            }

        }
        EzyArray array = requestSerializer.serialize(cmd, data);
        if (socketClient != null) {
            socketClient.sendMessage(array, shouldEncrypted);
            printSentData(cmd, data);
        }
    }

    @Override
    public void processEvents() {
        socketClient.processEventMessages();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public EzyClientConfig getConfig() {
        return config;
    }

    @Override
    public boolean isEnableSSL() {
        return config.isEnableSSL();
    }

    @Override
    public boolean isEnableDebug() {
        return config.isEnableDebug();
    }

    @Override
    public EzyZone getZone() {
        return zone;
    }

    @Override
    public void setZone(EzyZone zone) {
        this.zone = zone;
    }

    @Override
    public EzyUser getMe() {
        return me;
    }

    @Override
    public void setMe(EzyUser me) {
        this.me = me;
    }

    @Override
    public EzyConnectionStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(EzyConnectionStatus status) {
        this.status = status;
    }

    @Override
    public boolean isConnected() {
        return status == EzyConnectionStatus.CONNECTED;
    }

    @Override
    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
        this.socketClient.setSessionId(sessionId);
    }

    @Override
    public long getSessionId() {
        return sessionId;
    }

    @Override
    public void setSessionToken(String token) {
        this.sessionToken = token;
        this.socketClient.setSessionToken(sessionToken);
    }

    @Override
    public String getSessionToken() {
        return sessionToken;
    }

    @Override
    public void setSessionKey(byte[] sessionKey) {
        this.sessionKey = sessionKey;
        this.socketClient.setSessionKey(sessionKey);
    }

    @Override
    public byte[] getSessionKey() {
        return sessionKey;
    }

    @Override
    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public byte[] getPublicKey() {
        return publicKey;
    }

    @Override
    public void setPrivateKey(byte[] privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public byte[] getPrivateKey() {
        return privateKey;
    }

    @Override
    public EzyApp getApp() {
        return zone != null ? zone.getApp() : null;
    }

    @Override
    public EzyApp getAppById(int appId) {
        if (zone != null) {
            EzyAppManager appManager = zone.getAppManager();
            EzyApp app = appManager.getAppById(appId);
            return app;
        }
        return null;
    }

    @Override
    public EzyPingManager getPingManager() {
        return pingManager;
    }

    @Override
    public EzyPingSchedule getPingSchedule() {
        return pingSchedule;
    }

    @Override
    public EzyHandlerManager getHandlerManager() {
        return handlerManager;
    }

    private void printSentData(EzyCommand cmd, EzyArray data) {
        if (!unloggableCommands.contains(cmd))
            EzyLogger.debug("send command: " + cmd + " and data: " + data);
    }
}
