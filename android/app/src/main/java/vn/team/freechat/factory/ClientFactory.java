package vn.team.freechat.factory;

import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyClients;
import com.tvd12.ezyfoxserver.client.command.EzyAppSetup;
import com.tvd12.ezyfoxserver.client.command.EzySetup;
import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;
import com.tvd12.ezyfoxserver.client.constant.EzyCommand;
import com.tvd12.ezyfoxserver.client.entity.EzyApp;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyData;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;
import com.tvd12.ezyfoxserver.client.event.EzyDisconnectionEvent;
import com.tvd12.ezyfoxserver.client.event.EzyEventType;
import com.tvd12.ezyfoxserver.client.event.EzyLostPingEvent;
import com.tvd12.ezyfoxserver.client.event.EzyTryConnectEvent;
import com.tvd12.ezyfoxserver.client.handler.EzyAccessAppHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyAppDataHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyConnectionFailureHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyConnectionSuccessHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyDisconnectionHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyEventHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyLoginSuccessHandler;
import com.tvd12.ezyfoxserver.client.request.EzyAccessAppRequest;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;

import vn.team.freechat.Mvc;
import vn.team.freechat.contant.Commands;
import vn.team.freechat.controller.ConnectionController;
import vn.team.freechat.controller.ContactController;
import vn.team.freechat.controller.MessageController;
import vn.team.freechat.handler.HandshakeHandler;

/**
 * Created by tavandung12 on 10/7/18.
 */

public class ClientFactory {

    private final Mvc mvc = Mvc.getInstance();
    private static final ClientFactory INSTANCE = new ClientFactory();

    private ClientFactory() {
    }

    public static ClientFactory getInstance() {
        return INSTANCE;
    }

    public EzyClient newClient(EzyRequest loginRequest) {
        final ConnectionController connectionController = mvc.getConnectionController();
        final ContactController contactController = mvc.getContactController();
        final MessageController messageController = mvc.getMessageController();
        final EzyClientConfig config = EzyClientConfig.builder()
                .zoneName("freechat")
                .build();
        final EzyClients clients = EzyClients.getInstance();
        EzyClient oldClient = clients.getDefaultClient();
        final EzyClient client = oldClient != null ? oldClient : clients.newDefaultClient(config);
        final EzySetup setup = client.get(EzySetup.class);
        setup.addEventHandler(EzyEventType.CONNECTION_SUCCESS, new EzyConnectionSuccessHandler() {
            @Override
            protected void postHandle() {
                connectionController.handleConnectSuccessfully();
            }
        });
        setup.addEventHandler(EzyEventType.CONNECTION_FAILURE, new EzyConnectionFailureHandler());
        setup.addEventHandler(EzyEventType.DISCONNECTION, new EzyDisconnectionHandler() {
            @Override
            protected void preHandle(EzyDisconnectionEvent event) {
                connectionController.handleServerNotResponding();
            }
        });
        setup.addDataHandler(EzyCommand.HANDSHAKE, new HandshakeHandler(loginRequest));
        setup.addDataHandler(EzyCommand.LOGIN, new EzyLoginSuccessHandler() {
            @Override
            protected void handleLoginSuccess(EzyData responseData) {
                EzyRequest request = new EzyAccessAppRequest("freechat");
                client.send(request);
            }
        });
        setup.addDataHandler(EzyCommand.APP_ACCESS, new EzyAccessAppHandler() {
            @Override
            protected void postHandle(EzyApp app, EzyArray data) {
                connectionController.handleAppAccessSuccessFully();
            }
        });
        setup.addEventHandler(EzyEventType.LOST_PING, new EzyEventHandler<EzyLostPingEvent>() {
            @Override
            public void handle(EzyLostPingEvent event) {
                connectionController.handleLostPing(event.getCount());
            }
        });

        setup.addEventHandler(EzyEventType.TRY_CONNECT, new EzyEventHandler<EzyTryConnectEvent>() {
            @Override
            public void handle(EzyTryConnectEvent event) {
                connectionController.handleTryConnect(event.getCount());
            }
        });

        EzyAppSetup appSetup = setup.setupApp("freechat");

        appSetup.addDataHandler("5", new EzyAppDataHandler<EzyObject>() {
            @Override
            public void handle(EzyApp app, EzyObject data) {
                contactController.handleGetContactsResponse(data);
            }
        });
        appSetup.addDataHandler(Commands.CHAT_SYSTEM_MESSAGE, new EzyAppDataHandler<EzyObject>() {
            @Override
            public void handle(EzyApp app, EzyObject data) {
                data.put("from", "System");
                messageController.handReceivedMessage(data);
            }
        });

        appSetup.addDataHandler(Commands.CHAT_USER_MESSAGE, new EzyAppDataHandler<EzyObject>() {
            @Override
            public void handle(EzyApp app, EzyObject data) {
                messageController.handReceivedMessage(data);
            }
        });
        return client;
    }

}
