import * as React from "react";
import Mvc from "mvc-es6";
import SocketRequest from "../../socket/SocketRequest";
import ToastView from "../components/ToastView";
import MyShortProfileView from "./MyShortProfileView";
import ContactListView from "./ContactListView";
import CurrentContactView from "./CurrentContactView";
import MessageListView from "./MessageListView";
import AddContactView from "./AddContactView";
import LogoSvg from "../../images/logo.svg";

class MessageView extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            message: "",
            keyword: "",
            contacts : [
                {channel: {channelId: 0, users: ["Bot"]}, lastMessage: ""}
            ],
            targetContact : 0,
            messagess : {[0] : [{value: "message 1st", reply: false}, {value: "message 2nd", reply: true}]}
        };
        this.contactDict = {};
        const {contacts} = this.state;
        contacts.forEach((contact) => {
            this.contactDict[contact.channel.channelId] = contact;
        });
        let mvc = Mvc.getInstance();
        this.chatController = mvc.getController("chat");
        this.messageController = mvc.getController("message");
        this.contactController = mvc.getController("contact");

        mvc.models.contactDict = this.contactDict;
    }

    componentDidMount() {
        this.messageController.addDefaultView("systemMessage", (msg) => {
            console.log("view add rev system message now");
            this.addReceivedBotMessage(msg);
        });

        this.messageController.addDefaultView("userMessage", (msg) => {
            console.log("view add rev user message now");
            this.addReceivedUserMessage(msg);
        });

        this.contactController.addDefaultView("newContacts", (newContacts) => {
            console.log("view add rev new contacts now");
            this.addContacts(newContacts);
        });

        this.chatController.addDefaultView("changeTarget", (target) => {
            console.log("view add rev change target now");
            this.updateTargetContact(target);
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
        this.chatController.removeAllViews();
        this.messageController.removeAllViews();
        this.contactController.removeDefaultView("newContacts");
    }

    addContacts(newContacts) {
        const {contacts, messagess} = this.state;
        const contactMap = this.contactDict;
        var updateContacts = [];
        newContacts.forEach(function(newContact) {
            const old = contactMap[newContact.channelId];
            if(!old) {
                const item = {channel: newContact, lastMessage: ""};
                contactMap[newContact.channelId] = item;
                updateContacts.push(item);
                messagess[newContact.channelId] = [];
                console.log("add new contact: " + JSON.stringify(newContact) + " success");
            }
        });
        updateContacts = contacts.concat(updateContacts);
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
                messagess[contactSearched.channelId] = [];
                console.log("searched contact: " + JSON.stringify(contactSearched) + " success");
            }
        });
        this.setState({contacts : searchContacts});
    }

    updateTargetContact(target) {
        var newTartget = target ? target : 0;
        this.setState({targetContact : newTartget});
        console.log('change contact, now target: ' + newTartget);
    }

    addAndSendMessageByEnter(e) {
        if (e.key === 'Enter') {
            this.addAndSendMessage()
        }
    }

    addAndSendMessageByButton(e) {
        this.addAndSendMessage()
    }

    addAndSendMessage() {
        var message = this.state.message;
        console.log("message input = " + message);
        this.setState({message : ""});
        this.addSentMessage({value: message, reply: false});
        this.sendMessage(message);
    }

    sendMessage(msg) {
        const { targetContact } = this.state;
        SocketRequest.sendMessageRequest(targetContact, msg);
    }

    addSentMessage(msg) {
        const { messagess, targetContact } = this.state;
        const messages = messagess[targetContact] || [];
        messages.push(msg);
        this.setState({ messagess: messagess });
    }

    addReceivedBotMessage(msg) {
        const { contacts, messagess } = this.state;
        const messages = messagess[0];
        const contact = this.contactDict[0];
        contact.lastMessage = msg.message;
        messages.push({value: msg.message, reply: true});
        this.setState({ contacts: contacts, messagess: messagess });
    }

    addReceivedUserMessage(msg) {
        const { messagess } = this.state;
        if(!this.contactDict[msg.channelId]) {
            let newContact = {channelId : msg.channelId, users: [msg.from]};
            this.addContacts([newContact]);
        }
        const messages = messagess[msg.channelId];
        const contact = this.contactDict[msg.channelId];
        contact.lastMessage = msg.message;
        messages.push({value: msg.message, reply: true});
        this.setState({ messagess: messagess });
        console.log('process rev user message done');
    }

    onAddContactClick(e) {
        this.contactController.updateViews("showAddContactsModal");
    }

    onUpdatePasswordClick(e) {
        this.toggleUpdatePasswordView();
    }

    render() {
        const {message, contacts, messagess, targetContact} = this.state;
        const currentContact = contacts[targetContact] || contacts[0];
        const messages = messagess[targetContact] || [];
        return (
            <div className="page-messages">
                <div className="messages-wrapper">
                    <ToastView/>
                    <header className="main-nav">
                        <div className="navbar-left">
                            <ul className="left-branding">
                                <li>
                                    <a href="#">
                                        <img src={LogoSvg} alt="logo" />
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div className="navbar navbar-right">
                        </div>
                    </header>
                    <section className="main-container">
                        <div className="sidepanel-wrapper">
                            <div className="sidepanel">
                                <MyShortProfileView parent={this} />
                                <ContactListView contacts={contacts} />
                                <div id="contact-actions">
                                    <button className="btn btn-icon"
                                        onClick={this.onAddContactClick.bind(this)}>
                                        <i className="fa-solid fa-user-plus"></i>
                                        <span>Add contact</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div className="content-wrapper">
                            <CurrentContactView data={currentContact} />
                            <MessageListView messages={messages} />
                            <div className="message-input input-group">
                                <input type="text" className="form-control"
                                    value={message}
                                    placeholder="Write your message..."
                                    onChange={this.onMessageChange.bind(this)}
                                    onKeyUp={this.addAndSendMessageByEnter.bind(this)}  />
                                <i className="icon-attachment attachment" aria-hidden="true"></i>
                                <button className="btn btn-send"
                                    id="buttonSendMessage"
                                    onClick={this.addAndSendMessageByButton.bind(this)}>
                                    <i className="fa-solid fa-paper-plane"></i>
                                </button>   
                            </div>
                        </div>
                    </section>
                    <footer className="footer-container">
                        <div>
                            © 2019 Free Chat created by <a href="https://youngmonkeys.org" target="_blank">youngmonkeys.org</a>.
                        </div>
                        <div>
                            <div className="label label-info">Version: 1.3.0</div>
                        </div>
                    </footer>
                    <AddContactView />
                </div>
            </div>
        );
    }
}

export default MessageView;
