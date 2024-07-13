import * as React from "react";
import Mvc from "mvc-es6";
import ContactView from "./ContactView";
import { SocketRequest } from "../../socket";

class ContactListView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            keyword: "",
            contacts : [
                {channel: {channelId: 0, users: ["Bot"]}, lastMessage: ""}
            ]
        };
        this.contactDict = {};
        const {contacts} = this.state;
        contacts.forEach((contact) => {
            this.contactDict[contact.channel.channelId] = contact;
        });
        this.mvc = Mvc.getInstance();
        this.contactController = this.mvc.getController("contact");

        this.mvc.models.contacts = contacts;
        this.mvc.models.contactDict = this.contactDict;
    }

    componentDidMount() {
        this.contactController.addDefaultView("newContacts", (newContacts) => {
            console.log("view add rev new contacts now");
            this.addContacts(newContacts);
        });

        this.contactController.addDefaultView("searchContacts", (contacts) => {
            console.log("view add rev search contacts now");
            this.searchContacts(contacts);
        });

        SocketRequest.requestGetContacts(0, 50);
    }

    onMessageChange(e) {
        this.setState({message: e.target.value});
    }

    componentWillUnmount() {
        this.contactController.removeDefaultView("newContacts");
        this.mvc.models.chat.currentContactView = null;
    }

    addContacts(newContacts) {
        const {contacts} = this.state;
        const contactMap = this.contactDict;
        var updateContacts = [];
        newContacts.forEach(function(newContact) {
            const old = contactMap[newContact.channelId];
            if(!old) {
                const item = {channel: newContact, lastMessage: ""};
                contactMap[newContact.channelId] = item;
                updateContacts.push(item);
                console.log("add new contact: " + JSON.stringify(newContact) + " success");
            }
        });
        updateContacts = contacts.concat(updateContacts);
        this.mvc.models.contacts = updateContacts;
        this.setState({contacts : updateContacts});
    }

    searchContacts(contacts) {
        const {messagess} = this.state;
        const contactMap = this.contactDict;
        var searchContacts = [{channel: {channelId: 0, users: ["Bot"]}, lastMessage: ""}];
        contacts.forEach(function(contactSearched) {
            const contactOld = contactMap[contactSearched.channelId];
            if(contactOld) {
                const item = {channel: contactSearched, lastMessage: ""};
                contactMap[contactSearched.channelId] = item;
                searchContacts.push(item);
                console.log("searched contact: " + JSON.stringify(contactSearched) + " success");
            }
        });
        this.setState({contacts : searchContacts});
    }

    onSearchChange(e) {
        var keyword = e.target.value;
        this.setState({keyword: keyword});
        if (keyword) {
            SocketRequest.searchContacts(keyword, 0, 30);
        } else {
            this.setState({contacts : this.mvc.models.contacts});
        }
    }

    render() {
        const {keyword, contacts} = this.state;
        return (
            <React.Fragment>
                <div className="input-group contact-search">
                    <span className="input-group-text">
                        <i className="fa-solid fa-magnifying-glass"></i>
                    </span>
                    <input type="text" id="search-user-keyword"
                        className="form-control" value={keyword} 
                        onChange={this.onSearchChange.bind(this)}
                        placeholder="Search contacts..." />
                </div>
                <div className="contacts-wrapper">
                    <div className="contacts" id="contacts">
                        <ul className="contact-list">
                        {
                            contacts.map((contact, i) => (
                                <ContactView key={i} data={contact} />
                            ))
                        }
                        </ul>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default ContactListView;