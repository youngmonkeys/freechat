package vn.team.freechat.socket;

import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyClients;
import com.tvd12.ezyfoxserver.client.handler.EzyHandshakeHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyLoginErrorHandler;
import com.tvd12.ezyfoxserver.client.request.EzyLoginRequest;
import com.tvd12.ezyfoxserver.client.setup.EzyAppSetup;
import com.tvd12.ezyfoxserver.client.setup.EzySetup;
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
import com.tvd12.ezyfoxserver.client.handler.EzyAppAccessHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyAppDataHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyConnectionFailureHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyConnectionSuccessHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyDisconnectionHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyEventHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyLoginSuccessHandler;
import com.tvd12.ezyfoxserver.client.request.EzyAppAccessRequest;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;

import vn.team.freechat.data.MessageReceived;
import vn.team.freechat.mvc.IController;
import vn.team.freechat.mvc.IModel;
import vn.team.freechat.mvc.Mvc;
import vn.team.freechat.constant.Commands;

/**
 * Created by tavandung12 on 10/7/18.
 */

public class SocketClientProxy {

    private final Mvc mvc = Mvc.getInstance();
    private static final SocketClientProxy INSTANCE = new SocketClientProxy();

    private SocketClientProxy() { }

    public static SocketClientProxy getInstance() {
        return INSTANCE;
    }

    public void setup() {
        final IController connectionController = mvc.getController("connection");
        final IController contactController = mvc.getController("contact");
        final IController messageController = mvc.getController("message");
        final EzyClientConfig config = EzyClientConfig.builder()
                .zoneName("freechat")
                .build();
        final EzyClients clients = EzyClients.getInstance();
        EzyClient oldClient = clients.getDefaultClient();
        final EzyClient client = oldClient != null ? oldClient : clients.newDefaultClient(config);
        final EzySetup setup = client.setup();
        setup.addEventHandler(EzyEventType.CONNECTION_SUCCESS, new EzyConnectionSuccessHandler() {
            @Override
            protected void postHandle() {
                connectionController.updateView("hide-loading");
            }
        });
        setup.addEventHandler(EzyEventType.CONNECTION_FAILURE, new EzyConnectionFailureHandler());
        setup.addEventHandler(EzyEventType.DISCONNECTION, new EzyDisconnectionHandler() {
            @Override
            protected void preHandle(EzyDisconnectionEvent event) {
                connectionController.updateView("show-loading");
            }

            @Override
            protected boolean shouldReconnect(EzyDisconnectionEvent event) {
                return super.shouldReconnect(event);
            }
        });
        setup.addDataHandler(EzyCommand.HANDSHAKE, new EzyHandshakeHandler() {
            @Override
            protected EzyRequest getLoginRequest() {
                IModel model = mvc.getModel();
                IModel connection = model.get("connection");
                return new EzyLoginRequest(
                        "freechat",
                        (String)connection.get("username"),
                        (String)connection.get("password")
                );
            }
        });
        setup.addDataHandler(EzyCommand.LOGIN, new EzyLoginSuccessHandler() {

            @Override
            protected void handleLoginSuccess(EzyData responseData) {
                EzyRequest request = new EzyAppAccessRequest("freechat");
                client.send(request);
            }
        });
        setup.addDataHandler(EzyCommand.LOGIN_ERROR, new EzyLoginErrorHandler() {
            @Override
            public void handle(EzyArray data) {
                super.handle(data);
            }
        });
        setup.addDataHandler(EzyCommand.APP_ACCESS, new EzyAppAccessHandler() {
            @Override
            protected void postHandle(EzyApp app, EzyArray data) {
                connectionController.updateView("show-contacts");
            }
        });
        setup.addEventHandler(EzyEventType.LOST_PING, new EzyEventHandler<EzyLostPingEvent>() {
            @Override
            public void handle(EzyLostPingEvent event) {
                connectionController.updateView("show-lost-ping", event.getCount());
            }
        });

        setup.addEventHandler(EzyEventType.TRY_CONNECT, new EzyEventHandler<EzyTryConnectEvent>() {
            @Override
            public void handle(EzyTryConnectEvent event) {
                connectionController.updateView("show-try-connect", event.getCount());
            }
        });

        EzyAppSetup appSetup = setup.setupApp("freechat");

        appSetup.addDataHandler(Commands.CHAT_GET_CONTACTS, new EzyAppDataHandler<EzyArray>() {
            @Override
            public void handle(EzyApp app, EzyArray contacts) {
                contactController.updateView("add-contacts", contacts);
            }
        });
        appSetup.addDataHandler(Commands.CHAT_SYSTEM_MESSAGE, new EzyAppDataHandler<EzyObject>() {
            @Override
            public void handle(EzyApp app, EzyObject data) {
                data.put("from", "System");
                messageController.updateView("add-message", MessageReceived.create(data));
            }
        });

        appSetup.addDataHandler(Commands.CHAT_USER_MESSAGE, new EzyAppDataHandler<EzyObject>() {
            @Override
            public void handle(EzyApp app, EzyObject data) {
                messageController.updateView("add-message", MessageReceived.create(data));
            }
        });
    }

    public void connect() {
        EzyClient client = getClient();
        client.connect("ws.tvd12.com", 3005);
    }

    public EzyClient getClient() {
        EzyClients clients = EzyClients.getInstance();
        return clients.getDefaultClient();
    }

}
