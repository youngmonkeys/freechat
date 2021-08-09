package com.tvd12.ezyfoxserver.client.socket;

import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.concurrent.EzyExecutors;
import com.tvd12.ezyfoxserver.client.constant.EzyDisconnectReason;
import com.tvd12.ezyfoxserver.client.event.EzyDisconnectionEvent;
import com.tvd12.ezyfoxserver.client.event.EzyEvent;
import com.tvd12.ezyfoxserver.client.event.EzyLostPingEvent;
import com.tvd12.ezyfoxserver.client.manager.EzyPingManager;
import com.tvd12.ezyfoxserver.client.request.EzyPingRequest;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;
import com.tvd12.ezyfoxserver.client.logger.EzyLogger;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by tavandung12 on 10/2/18.
 */

public class EzyPingSchedule {

    protected final EzyClient client;
    protected final EzyRequest request;
    protected final EzyPingManager pingManager;
    protected ScheduledFuture<?> scheduledFuture;
    protected EzySocketEventQueue socketEventQueue;
    protected final ScheduledExecutorService scheduledExecutor;

    public EzyPingSchedule(EzyClient client) {
        this.client = client;
        this.request = new EzyPingRequest();
        this.pingManager = client.getPingManager();
        this.scheduledExecutor = newScheduledExecutor();

    }

    protected ScheduledExecutorService newScheduledExecutor() {
        final ScheduledExecutorService answer = EzyExecutors.newSingleThreadScheduledExecutor("ping-schedule");
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                answer.shutdown();
            }
        }));
        return answer;
    }

    public void start() {
        synchronized (this) {
            long periodMillis = pingManager.getPingPeriod();
            scheduledFuture = scheduledExecutor.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            sendPingRequest();
                        }
                    },
                    periodMillis, periodMillis, TimeUnit.MILLISECONDS);
        }
    }

    public void stop() {
        synchronized (this) {
            if (scheduledFuture != null)
                this.scheduledFuture.cancel(true);
            this.scheduledFuture = null;
        }
    }

    private void sendPingRequest() {
        int lostPingCount = pingManager.increaseLostPingCount();
        int maxLostPingCount = pingManager.getMaxLostPingCount();
        if(lostPingCount >= maxLostPingCount) {
            EzyEvent event = new EzyDisconnectionEvent(EzyDisconnectReason.SERVER_NOT_RESPONDING.getId());
            socketEventQueue.addEvent(event);
        }
        else {
            EzyRequest request = new EzyPingRequest();
            client.send(request);
        }
        if(lostPingCount > 1) {
            EzyLogger.info("lost ping count: " + lostPingCount);
            EzyLostPingEvent event = new EzyLostPingEvent(lostPingCount);
            socketEventQueue.addEvent(event);
        }
    }

    public void setSocketEventQueue(EzySocketEventQueue socketEventQueue) {
        this.socketEventQueue = socketEventQueue;
    }
}
