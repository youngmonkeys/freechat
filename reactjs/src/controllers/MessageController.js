import AppController from './AppController'

class MessageController extends AppController {
    constructor() {
        super();
        this.updateViewBySystemMessage = null;
        this.updateViewByUserMessage = null;
    }

    sendMessageRequest(target, message) {
        if(target === "System")
            this.sendSystemMessageRequest(message);
        else 
            this.sendUserMessageRequest(target, message);
    }
    
    sendSystemMessageRequest(message) {
        this.getApp().sendRequest("4", {message : message});
    }

    sendUserMessageRequest(target, message) {
        this.getApp().sendRequest("6", {message : message, to : target});
    }

    handleSystemMessageResponse(data) {
        var {message} = data;
        console.log("received message: " + message + ", update view now");
        this.updateViewBySystemMessage(message);
    }

    handleUserMessageResponse(data) {
        const {from, message} = data;
        console.log("received message: " + message + " from: " + from + ", update view now");
        this.updateViewByUserMessage(data);
    }

}

export default MessageController