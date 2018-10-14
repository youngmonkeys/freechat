import MessageController from './controllers/MessageController';
import DisconnectController from './controllers/DisconnectController';
import LoginController from './controllers/LoginController';
import ContactController from './controllers/ContactController';
import MyProfileController from './controllers/MyProfileController'

class Controller {

    constructor() {
        this.loginController = new LoginController();
        this.messageController = new MessageController();
        this.disconnectController = new DisconnectController();
        this.contactController = new ContactController();
        this.myProfileController = new MyProfileController();
    }

    handleAccessAppSuccess(app) {
        this.loginController.updateView();
    }

    handleDisconnection(reason) {
        this.disconnectController.handleDisconnection(reason);
    }

}

class Mvc {

    static instance = null;

    constructor() {
        this.view = "";
        this.controller = new Controller();
    }

    static getInstance() {
        if(!Mvc.instance)
            Mvc.instance = new Mvc();
        return Mvc.instance;
    }

}

export default Mvc;