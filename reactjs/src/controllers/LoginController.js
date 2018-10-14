import ClientFactory from '../socket/ClientFactory';

class LoginController {
    
    constructor() {
        this.updateView = null;
    }

    handleConnectAndLogin(data) {
        var clientFactory = new ClientFactory();
        clientFactory.setCredentials(data.username, data.password);
        var client = clientFactory.createClient();
        client.connect("ws://localhost:2208/ws");
    }

}

export default LoginController