// import Ezy from '../lib/ezyfox-server-es6-client'
import Ezy from 'ezyfox-es6-client';

class AppController {
    constructor() {
        this.clients = Ezy.Clients.getInstance();
    }

    getClients() {
        const clients = Ezy.Clients.getInstance();
        return clients;
    }

    getClient() {
        const clients = this.getClients();
        const client = clients.getDefaultClient();
        return client;
    }

    getZone() {
        const client = this.getClient();
        const zone = client.zone;
        return zone;
    }

    getApp() {
        const zone = this.getZone();
        const appManager = zone.appManager;
        const app = appManager.getApp();
        return app;
    }

    getMe() {
        const client = this.getClient();
        return client.me;
    }
}

export default AppController;