// import Ezy from '../lib/ezyfox-server-es6-client'
import Ezy from 'ezyfox-es6-client';
import {Command} from "./SocketConstants";

class SocketRequestClass {

    requestSuggestionContacts() {
        this.getApp().sendRequest(Command.SUGGEST_CONTACTS);
    }

    requestUpdatePassword(oldPassword, newPassword) {
        this.getApp().sendRequest(
            Command.UPDATE_PASSWORD,
            {"oldPassword": oldPassword, "newPassword": newPassword}
        );
    }

    searchContacts(keyword, skip, limit) {
        this.getApp().sendRequest(
            Command.SEARCH_CONTACTS,
            {keyword: keyword, skip: skip, limit: limit}
        );
    }

    searchContactsUsers(keyword, skip, limit) {
        this.getApp().sendRequest(
            Command.SEARCH_CONTACTS_USER,
            {keyword: keyword, skip: skip, limit: limit}
        );
    }

    requestAddContacts(target) {
        this.getApp().sendRequest(Command.ADD_NEW_CONTACTS, {target: target});
    }

    requestGetContacts(skip, limit) {
        this.getApp().sendRequest(Command.GET_CONTACTS, {skip: skip, limit: limit});
    }

    sendMessageRequest(channelId, message) {
        if (channelId === 0)
            this.sendSystemMessageRequest(message);
        else
            this.sendUserMessageRequest(channelId, message);
    }

    sendSystemMessageRequest(message) {
        this.getApp().sendRequest(Command.SEND_RECEIVE_SYSTEM_MESSAGE, {message: message});
    }

    sendUserMessageRequest(target, message) {
        this.getApp().sendRequest(
            Command.SEND_RECEIVE_USER_MESSAGE,
            {message: message, channelId: target}
        );
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

var SocketRequest = SocketRequest || new SocketRequestClass();

export default SocketRequest;
