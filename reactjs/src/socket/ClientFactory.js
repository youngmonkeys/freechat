// import Ezy from '../lib/ezyfox-server-es6-client'
import Ezy from 'ezyfox-es6-client';
import Mvc from '../Mvc';

class ClientFactory {
    setCredentials(username, password) {
        this.username = username;
        this.password = password;
    }

    createClient() {
        var controller = Mvc.getInstance().controller;
        var handshakeHandler = new Ezy.HandshakeHandler();
        var username = this.username;
        var password = this.password;
        handshakeHandler.getLoginRequest = function() {
            return ["freechat", username, password, []];
        }

        var userLoginHandler = new Ezy.LoginSuccessHandler();
        userLoginHandler.handleLoginSuccess = function() {
            var accessAppRequest = ["freechat", []];
            this.client.sendRequest(Ezy.Command.APP_ACCESS, accessAppRequest);
        }

        var accessAppHandler = new Ezy.AppAccessHandler();
        accessAppHandler.postHandle = function(app, data) {
            controller.handleAccessAppSuccess(app);
        }

        var disconnectionHandler = new Ezy.DisconnectionHandler();
        disconnectionHandler.preHandle = function(event) {
            console.log("custom handleDisconnection")
            controller.handleDisconnection(event.reason);
        }

        var config = new Ezy.ClientConfig;
        config.zoneName = "freechat";
        var clients = Ezy.Clients.getInstance();
        var client = clients.newDefaultClient(config);
        var setup = client.setup;
        setup.addEventHandler(Ezy.EventType.DISCONNECTION, disconnectionHandler);
        setup.addDataHandler(Ezy.Command.HANDSHAKE, handshakeHandler);
        setup.addDataHandler(Ezy.Command.LOGIN, userLoginHandler);
        setup.addDataHandler(Ezy.Command.APP_ACCESS, accessAppHandler);
        var setupApp = setup.setupApp("freechat");
        setupApp.addDataHandler("1", function(app, data) {
            controller.contactController.handleSuggestedContactsResponse(data);
        });
        
        setupApp.addDataHandler("2", function(app, data) {
            controller.contactController.handleAddContactsResponse(data);
        });
        
        setupApp.addDataHandler("4", function(app, data) {
            controller.messageController.handleSystemMessageResponse(data);
        });
        
        setupApp.addDataHandler("5", function(app, data) {
            controller.contactController.handleContactsResponse(data);
        });
        
        setupApp.addDataHandler("6", function(app, data) {
            controller.messageController.handleUserMessageResponse(data);
        });
        return client;
    }
}

export default ClientFactory