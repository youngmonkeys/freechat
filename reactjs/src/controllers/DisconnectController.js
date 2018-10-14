class DisconnectController {
    constructor() {
        this.updateView = () => {};
    }

    handleDisconnection(reason) {
        this.updateView(reason);
    }
}

export default DisconnectController;