import AppController from './AppController'

class ContactController extends AppController {
    constructor() {
        super();
        this.updateSuggestionView = null;
        this.updateContactView = null;
    }

    handleSuggestionContacts() {
        this.getApp().sendRequest('1', {});
    }

    handleAddContacts(target) {
        this.getApp().sendRequest('2', {'target': target});
    }

    handleGetContacts(skip, limit) {
        this.getApp().sendRequest('5', {skip: skip, limit: limit});
    }

    handleSuggestedContactsResponse(data) {
        this.updateSuggestionView(data['users']);
    }

    handleAddContactsResponse(data) {
        this.updateContactView(data['new-contacts']);
    }

    handleContactsResponse(data) {
        this.updateContactView(data['contacts']);
    }
}

export default ContactController;