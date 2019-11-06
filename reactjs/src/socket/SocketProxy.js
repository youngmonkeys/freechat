// import Ezy from '../lib/ezyfox-server-es6-client'
import Ezy from 'ezyfox-es6-client';
import Mvc from '../Mvc';

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

        let userLoginHandler = new Ezy.LoginSuccessHandler();
        userLoginHandler.handleLoginSuccess = function() {
            let accessAppRequest = ["freechat", []];
            this.client.sendRequest(Ezy.Command.APP_ACCESS, accessAppRequest);
        }

        let accessAppHandler = new Ezy.AppAccessHandler();
        accessAppHandler.postHandle = function(app, data) {
            let loginController = mvc.getController("login");
            loginController.updateViews('connect');
        }

        let disconnectionHandler = new Ezy.DisconnectionHandler();
        disconnectionHandler.preHandle = function(event) {
            console.log("custom disconnection handler")
            let disconnectController = mvc.getController("disconnect");
            disconnectController.updateViews("disconnect");
        }

        let config = new Ezy.ClientConfig;
        config.zoneName = "freechat";
        let clients = Ezy.Clients.getInstance();
        let client = clients.newDefaultClient(config);
        let setup = client.setup;
        setup.addEventHandler(Ezy.EventType.DISCONNECTION, disconnectionHandler);
        setup.addDataHandler(Ezy.Command.HANDSHAKE, handshakeHandler);
        setup.addDataHandler(Ezy.Command.LOGIN, userLoginHandler);
        setup.addDataHandler(Ezy.Command.APP_ACCESS, accessAppHandler);
        let setupApp = setup.setupApp("freechat");

        let messageController = mvc.getController("message");
        let contactController = mvc.getController("contact");

        setupApp.addDataHandler("1", function(app, data) {
            contactController.updateViews("suggestion", data['users']);
        });
        
        setupApp.addDataHandler("2", function(app, data) {
            let contacts = data['new-contacts'];
            console.log("add news contacts: " + JSON.stringify(contacts));
            contactController.updateViews("newContacts", contacts);
        });
        
        setupApp.addDataHandler("4", function(app, data) {
            let {message} = data;
            console.log("received message: " + message + ", update view now");
            messageController.updateViews("systemMessage", message);
        });
        
        setupApp.addDataHandler("5", function(app, data) {
            contactController.updateViews("newContacts", data['contacts']);
        });
        
        setupApp.addDataHandler("6", function(app, data) {
            const {from, message} = data;
            console.log("received message: " + message + " from: " + from + ", update view now");
            messageController.updateViews("userMessage", data);
        });
        return client;
    }

    connect() {
        let url = "wss://ws.tvd12.com/ws";
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