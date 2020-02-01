// import Ezy from '../lib/ezyfox-server-es6-client'
import Ezy from 'ezyfox-es6-client';
import Mvc from 'mvc-es6';

class SocketProxy {
    
    static instance = null;

    static getInstance() {
        if (!SocketProxy.instance)
            SocketProxy.instance = new SocketProxy();
        return SocketProxy.instance;
    }

    setup() {
        let mvc = Mvc.getInstance();
        let models = mvc.models;
        let handshakeHandler = new Ezy.HandshakeHandler();
        handshakeHandler.getLoginRequest = function() {
            let connection = models.connection;
            let username = connection.username;
            let password = connection.password;
            return ["freechat", username, password, []];
        }

        let loginSuccessHandler = new Ezy.LoginSuccessHandler();
        loginSuccessHandler.handleLoginSuccess = function() {
            let accessAppRequest = ["freechat", []];
            this.client.sendRequest(Ezy.Command.APP_ACCESS, accessAppRequest);
        }

        let loginErrorHandler = new Ezy.LoginErrorHandler();
        loginErrorHandler.handleLoginError = function(event) {
            let loginController = mvc.getController("login");
            loginController.updateViews("loginError", event[1]);
        };

        let accessAppHandler = new Ezy.AppAccessHandler();
        accessAppHandler.postHandle = function(app, data) {
            let routerController = mvc.getController("router");
            routerController.updateViews('change', '/message');
        }

        let disconnectionHandler = new Ezy.DisconnectionHandler();
        disconnectionHandler.preHandle = function(event) {
            let routerController = mvc.getController("router");
            routerController.updateViews('change', '/');
        }
        let shouldReconnectParent = disconnectionHandler.shouldReconnect;
        disconnectionHandler.shouldReconnect = function(event) {
            var reason = event.reason;
            if(reason == 401)
                return false;
            return shouldReconnectParent(event);
        }

        let config = new Ezy.ClientConfig;
        config.zoneName = "freechat";
        let clients = Ezy.Clients.getInstance();
        let client = clients.newDefaultClient(config);
        let setup = client.setup;
        setup.addEventHandler(Ezy.EventType.DISCONNECTION, disconnectionHandler);
        setup.addDataHandler(Ezy.Command.HANDSHAKE, handshakeHandler);
        setup.addDataHandler(Ezy.Command.LOGIN, loginSuccessHandler);
        setup.addDataHandler(Ezy.Command.LOGIN_ERROR, loginErrorHandler);
        setup.addDataHandler(Ezy.Command.APP_ACCESS, accessAppHandler);
        let setupApp = setup.setupApp("freechat");

        let messageController = mvc.getController("message");
        let contactController = mvc.getController("contact");

        setupApp.addDataHandler("1", function(app, data) {
            contactController.updateViews("suggestion", data['users']);
        });
        
        setupApp.addDataHandler("2", function(app, data) {
            console.log("add news contacts: " + JSON.stringify(data));
            contactController.updateViews("newContacts", data);
        });
        
        setupApp.addDataHandler("4", function(app, data) {
            console.log("received message: " + JSON.stringify(data) + ", update view now");
            messageController.updateViews("systemMessage", data);
        });
        
        setupApp.addDataHandler("5", function(app, data) {
            contactController.updateViews("newContacts", data);
        });
        
        setupApp.addDataHandler("6", function(app, data) {
            console.log("received message: " + JSON.stringify(data) + ", update view now");
            messageController.updateViews("userMessage", data);
        });
        return client;
    }

    connect() {
        let url = "wss://ws.tvd12.com/ws";
        // let url = "ws://localhost:2208/ws";
        let client = this.getClient();
        client.connect(url);
    }

    isConnected() {
        let client = this.getClient();
        let connected = client && client.isConnected();
        return connected;
    }

    getClient() {
        let clients = Ezy.Clients.getInstance();
        let client = clients.getDefaultClient();
        return client;
    }
}

export default SocketProxy;