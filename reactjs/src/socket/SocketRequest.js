// import Ezy from '../lib/ezyfox-server-es6-client'
import Ezy from 'ezyfox-es6-client';

class SocketRequestClass {

    requestSuggestionContacts() {
        this.getApp().sendRequest('1');
    }

    requestAddContacts(target) {
        this.getApp().sendRequest('2', {'target': target});
    }

    requestGetContacts(skip, limit) {
        this.getApp().sendRequest('5', {skip: skip, limit: limit});
    }

    sendMessageRequest(channelId, message) {
        if(channelId === 0)
            this.sendSystemMessageRequest(message);
        else 
            this.sendUserMessageRequest(channelId, message);
    }
    
    sendSystemMessageRequest(message) {
        this.getApp().sendRequest("4", {message : message});
    }

    sendUserMessageRequest(target, message) {
        this.getApp().sendRequest("6", {message : message, channelId : target});
    }

    getClient() {
        let clients = Ezy.Clients.getInstance();
        let client = clients.getDefaultClient();
        return client;
    }

    getApp() {
        let zone = this.getClient().zone;
        let appManager = zone.appManager;
        let app = appManager.getApp();
        return app;
    }

}

let SocketRequest = SocketRequest || new SocketRequestClass();

export default SocketRequest;