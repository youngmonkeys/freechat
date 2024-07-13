import * as React from "react";
import Mvc from "mvc-es6";
import { SocketProxy, SocketRequest } from "../../socket";
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
            targetContact : 0,
            messagess : {[0] : [{value: "message 1st", reply: false}, {value: "message 2nd", reply: true}]}
        };
        this.mvc = Mvc.getInstance();
        this.chatController = this.mvc.getController("chat");
        this.contactController = this.mvc.getController("contact");
        this.messageController = this.mvc.getController("message");
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

        this.chatController.addDefaultView("changeTarget", (target) => {
            console.log("view add rev change target now");
            this.updateTargetContact(target);
        });
    }

    onMessageChange(e) {
        this.setState({message: e.target.value});
    }

    componentWillUnmount() {
        this.chatController.removeAllViews();
        this.messageController.removeAllViews();
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
        const contact = this.mvc.models.contactDict[0];
        contact.lastMessage = msg.message;
        messages.push({value: msg.message, reply: true});
        this.setState({ contacts: contacts, messagess: messagess });
    }

    addReceivedUserMessage(msg) {
        console.log("addReceivedUserMessage");
        var client = SocketProxy.getInstance().getClient();
        if (client.me.name == msg.sender) {
            return;
        }
        const { messagess } = this.state;
        const contactDict = this.mvc.models.contactDict;
        const contact = contactDict[msg.channelId];
        if(!contact) {
            let newContact = {channelId : msg.channelId, users: [msg.from]};
            this.addContacts([newContact]);
        }
        contact.lastMessage = msg.message;
        var messages = messagess[msg.channelId];
        if (!messages) {
            messages = [];
            messagess[msg.channelId] = messages;
        }
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
        const {message, messagess, targetContact} = this.state;
        const messages = messagess[targetContact] || [];
        return (
            <div className="page-messages">
                <div className="messages-wrapper">
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
                                <ContactListView />
                                <div className="contact-actions">
                                    <button className="btn btn-icon"
                                        onClick={this.onAddContactClick.bind(this)}>
                                        <i className="fa-solid fa-user-plus"></i>
                                        <span>Add contact</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div className="content-wrapper">
                            <CurrentContactView />
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
