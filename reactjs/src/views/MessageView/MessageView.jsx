import * as React from 'react';
import $ from 'jquery'
import Mvc from 'mvc-es6';
import AddContactView from './AddContactView';
import SocketProxy from '../../socket/SocketProxy';
import SocketRequest from '../../socket/SocketRequest';
import UpdatePasswordView from "./UpdatePasswordView";
import ToastView from "../components/ToastView";

class MessageItemView extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {data} = this.props;
        return (
            data.reply
            ?
            (
                <li className="replies">
                    <img src={require('../../images/50x50.png')} alt="" />
                    <p>{data.value}</p>
                </li>
            )
            :
            (
                <li className="sent">
                    <img src={require('../../images/50x50.png')} alt="" />
                    <p>{data.value}</p>
                </li>
            )
        );
    }
}

class MessageListView extends React.Component {
    constructor(props) {
        super(props);
    }

    componentWillUpdate(nextProps) {
        // check scroll logic
    }

    componentDidUpdate() {
        this.scrollToBottom();
    }

    scrollToBottom() {
        const messages = document.querySelector("#messageListDiv")
        const height = messages.scrollHeight;
        $(messages).animate({scrollTop: height}, "fast");
    }

    render()  {
        const {messages} = this.props;
        return (
            <div className="messages" id="messageListDiv">
                <ul id="messageListUl">
                    {
                        messages.map((msg, i) => <MessageItemView key={i} data={msg} />)
                    }
                </ul>
            </div>
        );
    }
}

class ContactView extends React.Component {
    constructor(props) {
        super(props);
        this.parent = props.parent;
        this.state = {selected : false};
        this.data = props.data;
        this.onClick = this.onClick.bind(this);
        this.mvc = Mvc.getInstance();
        this.chatController = this.mvc.getController("chat");
    }

    unselect() {
        this.setState({selected : false});
    }

    onClick(e) {
        let chatModel = this.mvc.models.chat;
        var currentContactView = chatModel.currentContactView;
        if(currentContactView == this)
            return;
        if(currentContactView != null)
            currentContactView.unselect();
        chatModel.currentContactView = this;
        let channelId = this.data.channel.channelId;
        let {selected} = this.state;
        let newSelected = !selected;
        this.setState({selected : newSelected});
        this.chatController.updateViews("changeTarget", channelId);
    }

    render() {
        const {data} = this.props;
        const {selected} = this.state;
        const activeClass = selected ? "active" : "";
        return (
            <li className={"contact " + activeClass} onClick={this.onClick}>
                <div className="wrap">
                    <span className="contact-status online"></span>
                    <img src={require('../../images/70x70.png')} alt="" />
                    <div className="meta">
                        <p className="name">{data.channel.users[0]}</p>
                        <p className="preview">{data.lastMessage}</p>
                    </div>
                </div>
            </li>
        );
    }
}

class ContactListView extends React.Component {
    constructor(props) {
        super(props);
        this.mvc = Mvc.getInstance();
    }

    componentWillUnmount() {
        this.mvc.models.chat.currentContactView = null;
    }

    render() {
        const {contacts} = this.props;
        return (
            <div id="contacts" >
                <ul id="ul-contacts">
                {
                    contacts.map((contact, i) => (
                        <ContactView key={i} data={contact} />
                    ))
                }
                </ul>
            </div>
        );
    }
}

class CurrentContactView extends React.Component {
    constructor(props) {
        super(props);
        let mvc = Mvc.getInstance();
        this.chatController = mvc.getController("chat");
        this.contactDict = mvc.models.contactDict;
        this.state = props.data;
    }

    componentDidMount() {
        this.chatController.addView("changeTarget", 'profile', (target) => {
            let channel = this.contactDict[target].channel;
            console.log("chat profile now is: " + JSON.stringify(channel));
            this.setState({channel: channel});
        });
    }

    componentWillUnmount() {
        this.chatController.removeView("changeTarget", 'profile');
    }

    render() {
        const {channel} = this.state;
        return (
            <div className="contact-profile">
                <img src={require('../../images/70x70.png')} alt="" />
                <div id="divGroupReceiver"><p id="receiver">{channel.users[0]}</p></div>
                <div className="social-media">
                    <i className="icon-facebook" aria-hidden="true"></i>
                    <i className="icon-twitter" aria-hidden="true"></i>
                    <i className="icon-instagram" aria-hidden="true"></i>
                </div>
            </div>
        );
    }
}

class MyShortProfileView extends React.Component {
    constructor(props) {
        super(props);
        this.parent = props.parent;
        let client = SocketProxy.getInstance().getClient();
        let me = client.me;
        this.data = {
            username : me.name
        };
    }

    render() {
        const data = this.data;
        return (
            <div id="profile">
                <div className="wrap">
                    <a href="#"><img id="profile-img" src={require('../../images/80x80.png')} className="online" alt="" /></a>
                    <p id ="userName">{data.username}</p>
                    <i className="icon-arrow-down32 expand-button" aria-hidden="true"></i>
                    <div id="status-options">
                    <ul>
                        <li id="status-online" className="active">
                            <span className="status-circle"></span>
                            <p>Online</p>
                        </li>
                        <li id="status-away">
                            <span className="status-circle"></span>
                            <p>Away</p>
                        </li>
                        <li id="status-busy">
                            <span className="status-circle"></span>
                            <p>Busy</p>
                        </li>
                        <li id="status-offline">
                            <span className="status-circle"></span>
                            <p>Offline</p>
                        </li>
                    </ul>
                    </div>
                    <div id="expanded">
                    <label><i className="icon-facebook" aria-hidden="true"></i></label>
                    <input name="twitter" type="text" defaultValue="facebook" />
                    <label><i className="icon-twitter" aria-hidden="true"></i></label>
                    <input name="twitter" type="text" defaultValue="twitter" />
                    <label><i className="icon-instagram" aria-hidden="true"></i></label>
                    <input name="twitter" type="text" defaultValue="instagram" />
                    </div>
                </div>
            </div>
        );
    }
}

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
            messagess : {[0] : [{value: "message 1st", reply: false}, {value: "message 2nd", reply: true}]},
            toggle: false,
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

    onSearchChange(e) {
        var keyword = e.target.value;
        this.setState({keyword: keyword});
        SocketRequest.searchContacts(keyword, 0, 30);
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
        this.toggleAddContactView();
    }

    onUpdatePasswordClick(e) {
        this.toggleUpdatePasswordView();
    }

    handleToggle = () => {
        const { toggle } = this.state;
        this.setState ({
          toggle: !toggle,
        });
    };

    render() {
        const {message, keyword, contacts, messagess, targetContact, toggle} = this.state;
        const currentContact = contacts[targetContact] || contacts[0];
        const messages = messagess[targetContact] || [];
        return (
            <div className="messages-wrapper">
                <ToastView/>
                <header className="main-nav">
                    <div className="navbar-left">
                        <ul className="left-branding">
                            <li>
                                <button onClick={this.handleToggle}>
                                    <i className="fa fa-bars" />
                                </button>
                            </li>
                            <li>
                                <a href="index.html"><div className="logo"></div></a>
                            </li>
                        </ul>
                    </div>
                    <div className="navbar navbar-right">
                        <ul className="top-icons">
                            <li>
                                <a href="#" className="btn-top-search visible-xs">
                                    <i className="icon-search4"></i>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <span className="bubble">6</span><i className="icon-stack3"></i>
                                </a>
                            </li>
                            <li className="dropdown apps-dropdown hidden-xs">
                                <a href="#" className="dropdown-toggle" data-toggle="dropdown">
                                    <i className="icon-grid2"></i>
                                </a>
                                <div className="dropdown-menu">
                                </div>
                            </li>

                        </ul>
                    </div>
                </header>

                <section className="main-container">
                    <div className={`mc-wrapper ${toggle ? 'toggle' : ''}`}>
                        <div className="mc-sidepanel">
                            <div className="mc-sidepanel-content">
                                <MyShortProfileView parent={this} />
                                <div id="search">
                                    <label><i className="icon-search4" aria-hidden="true"></i></label>
                                    <input
                                        id="search-user-keyword"
                                        type="text"
                                        value={keyword}
                                        onChange={this.onSearchChange.bind(this)}
                                        placeholder="Search contacts..." />
                                </div>
                                <ContactListView contacts={contacts} />
                                <div id="bottom-bar">
                                    <button id="addcontact" onClick={this.onAddContactClick.bind(this)}>
                                        <i className="icon-user-plus" data-toggle="modal" data-target="#addContactsModel" aria-hidden="true"></i>
                                        <span>Add contact</span>
                                    </button>
                                    <div className="dropdown">
                                        <button id="setting" className="btn dropdown-toggle"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i className="icon-gear" aria-hidden="true"></i> <span>Settings</span>                                        </button>
                                        <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                            <span className="dropdown-item" onClick={this.onUpdatePasswordClick.bind(this)}>Update password</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="content-wrapper">
                            <div className="content">
                            <CurrentContactView data={currentContact} />
                            <MessageListView messages={messages} />
                            <div className="message-input">
                                <div className="wrap">
                                    <input type="text"
                                        value={message}
                                        placeholder="Write your message..."
                                        onChange={this.onMessageChange.bind(this)}
                                        onKeyPress={this.addAndSendMessageByEnter.bind(this)}  />
                                    <i className="icon-attachment attachment" aria-hidden="true"></i>
                                    <button className="submit"
                                        id="buttonSendMessage"
                                        onClick={this.addAndSendMessageByButton.bind(this)}>
                                        <i className="icon-paperplane" aria-hidden="true"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                        </div>
                    </div>
                </section>
                <footer className="footer-container">
                    <div className="row">
                        <div className="col-md-12 col-sm-12">
                            <div className="pull-left">
                                © 2019 Free Chat&nbsp;&nbsp;&nbsp;&bull;&nbsp;&nbsp;&nbsp;created by <a href="https://youngmonkeys.org" target="_blank">youngmonkeys.org</a>.							</div>
                            <div className="pull-right">
                                <div className="label label-info">Version: 1.3.0</div>
                            </div>
                        </div>
                    </div>
                </footer>
                {/* <a id="scrollTop" href="#top"><i className="icon-arrow-up12"></i></a> */}
                <AddContactView parent={this} />
                <UpdatePasswordView parent={this}/>
            </div>
        );
    }
}

export default MessageView
