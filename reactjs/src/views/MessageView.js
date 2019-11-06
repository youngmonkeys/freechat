import * as React from 'react';
import Mvc from '../Mvc';
import MessageScript from './js/Message';
import AddContactView from './AddContactView';
import SocketProxy from '../socket/SocketProxy';
import SocketRequest from '../socket/SocketRequest'

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
                    <img src={require('../images/50x50.png')} alt="" />
                    <p>{data.value}</p>
                </li>
            )
            :
            (
                <li className="sent">
                    <img src={require('../images/50x50.png')} alt="" />
                    <p>{data.value}</p>
                </li>
            )
        );
    }
}

class MessageListView extends React.Component { 
    constructor(props) {
        super(props);
        this.messageListRef = React.createRef();
        this.script = new MessageScript(); 
    }

    componentWillUpdate(nextProps) {
        // check scroll logic
    }
    
    componentDidUpdate() {
        this.script.scrollToBottom();
    }

    render()  {
        const {messages} = this.props;
        return (
            <div className="messages" id="messageListDiv" ref={this.messageListRef}>
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
        this.onClick = this.onClick.bind(this);
        this.controller = Mvc.getInstance().controller;
    }

    unselect() {
        this.setState({selected : false});
    }

    onClick(e) {
        const {selected} = this.state;
        const newSelected = !selected;
        this.parent.updateSelectedItem(this, newSelected);
        this.setState({selected : newSelected});
    }

    render() {
        const {data} = this.props;
        const {selected} = this.state;
        const activeClass = selected ? "active" : "";
        return (
            <li className={"contact " + activeClass} onClick={this.onClick}>
                <div className="wrap">
                    <span className="contact-status online"></span>
                    <img src={require('../images/70x70.png')} alt="" />
                    <div className="meta">
                        <p className="name">{data.username}</p>
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
        this.parent = props.parent;
        this.selectedItem = null;
    }

    updateSelectedItem(newSelectedItem, selected) {
        if(this.selectedItem) {
            if(newSelectedItem != this.selectedItem) 
                this.selectedItem.unselect();
        }
        this.selectedItem = selected ? newSelectedItem : null;
        var target = null;
        if(this.selectedItem)
            target = this.selectedItem.props.data.username;
        this.parent.updateTargetContact(target);
    }

    render() {
        const {contacts} = this.props;
        return (
            <div id="contacts" >
                <ul id="ul-contacts">
                {
                    contacts.map((contact, i) => (
                        <ContactView key={i} data={contact} parent={this} />
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
        this.parent = props.parent;
        this.controller = Mvc.getInstance().controller;
    }

    render() {
        const {data} = this.props;
        return (
            <div className="contact-profile">
                <img src={require('../images/70x70.png')} alt="" />
                <div id="divGroupReceiver"><p id="receiver">{data.username}</p></div>
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
        let mvc = Mvc.getInstance();
        this.myProfileController = mvc.getController("myProfile");
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
                    <a href="#"><img id="profile-img" src={require('../images/80x80.png')} className="online" alt="" /></a>
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
            contacts : [{username: "System", lastMessage: ""}],
            targetContact : "System",
            messagess : {"System" : [{value: "message 1st", reply: false}, {value: "message 2nd", reply: true}]},
        };
        
        this.contactDict = {};
        const {contacts} = this.state;
        contacts.forEach((contact) => {
            this.contactDict[contact.username] = contact;
        });

        let mvc = Mvc.getInstance();
        this.messageController = mvc.getController("message");
        this.contactController = mvc.getController("contact");        
        this.disconnectController = mvc.getController("disconnect");
    }

    componentDidMount() {
        this.messageController.addDefaultView("systemMessage", (msg) => {
            console.log("add rev system message now");
            this.addReceivedSystemMessage(msg);
        });
        this.messageController.addDefaultView("userMessage", (msg) => {
            console.log("add user system message now");
            this.addReceivedUserMessage(msg);
        });
        this.contactController.addDefaultView("newContacts", (newContacts) => {
            this.addContacts(newContacts);
        });
        this.disconnectController.addDefaultView("disconnect", (reason) => {
            console.log("disconnected, redirect to login view");
            this.props.history.push("/login");
        });

        SocketRequest.requestGetContacts(0, 50);
    }

    componentWillUnmount() {
        this.messageController.removeAllViews();
        this.disconnectController.removeAllViews();
        this.contactController.removeDefaultView("newContacts");
    }

    addContacts(newContacts) {
        console.log('add contacts: ' + newContacts);
        const {contacts, messagess} = this.state;
        const contactMap = this.contactDict;
        var updateContacts = [];
        newContacts.forEach(function(newContact) {
            const old = contactMap[newContact];
            if(!old) {
                const item = {username : newContact, lastMessage: ""};
                contactMap[newContact] = item;
                updateContacts.unshift(item);
                messagess[newContact] = [];
                console.log("add new contact: " + newContact + " success");
            }
        });
        updateContacts = updateContacts.concat(contacts);
        this.setState({contacts : updateContacts});
    }

    updateTargetContact(target) {
        var newTartget = target ? target : "System";
        this.setState({targetContact : newTartget});
        console.log('change contact, now target: ' + newTartget);
    }

    addAndSendMessage(e) {
        if (e.key === 'Enter') {
            var messageInput = this.refs.messageInput;
            var message = messageInput.value;
            console.log("message input = " + message);
            messageInput.value = "";
            this.addSentMessage({value: message, reply: false});
            this.sendMessage(message);
        }
    }

    sendMessage(msg) {
        const { targetContact } = this.state;
        SocketRequest.sendMessageRequest(targetContact, msg);
    }

    addSentMessage(msg) {
        const { messagess, targetContact } = this.state;
        const messages = messagess[targetContact];
        messages.push(msg);
        this.setState({ messagess: messagess });
    }
    
    addReceivedMessage(msg) {
        const { messagess, targetContact } = this.state;
        const messages = messagess[targetContact];
        messages.push(msg);
        this.setState({ messagess: messagess });
    }

    addReceivedSystemMessage(msg) {
        const { contacts, messagess } = this.state;
        const messages = messagess["System"];
        const contact = this.contactDict["System"];
        contact.lastMessage = msg;
        messages.push({value: msg, reply: true});
        this.setState({ contacts: contacts, messagess: messagess });
    }

    addReceivedUserMessage(msg) {
        const { contacts, messagess } = this.state;
        if(!messagess[msg.from]) {
            const item = {username : msg.from, lastMessage: ""};
            this.contactDict[msg.from] = item;
            contacts.unshift(item);
            messagess[msg.from] = [];
            console.log("add new contact: " + msg.from + " success");
        }
        const messages = messagess[msg.from];
        const contact = this.contactDict[msg.from];
        contact.lastMessage = msg.message;
        messages.push({value: msg.message, reply: true});
        this.setState({ contacts: contacts, messagess: messagess });
        console.log('process rev user message done');
    }

    onAddContactClick(e) {
        this.toggleAddContactView();
    }

    render() {
        const { contacts, messagess, targetContact } = this.state;
        const currentContact = this.contactDict[targetContact] ? this.contactDict[targetContact] : this.contactDict["System"];
        const messages = messagess[targetContact] ? messagess[targetContact] : [];
        return (
            <div>
                <header className="main-nav">
                    <div className="navbar-left">
                        <ul className="left-branding">
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
                    <div className="mc-wrapper">
                        <div className="mc-sidepanel">
                            <MyShortProfileView parent={this} />
                            <div id="search">
                                <label><i className="icon-search4" aria-hidden="true"></i></label>
                                <input id="search-user-keyword" type="text" placeholder="Search contacts..." />
                            </div>
                            <ContactListView contacts={contacts} parent={this} />
                            <div id="bottom-bar">
                                <button id="addcontact" onClick={this.onAddContactClick.bind(this)}>
                                    <i className="icon-user-plus" data-toggle="modal" data-target="#addContactsModel" aria-hidden="true"></i> 
                                    <span>Add contact</span>
                                </button>
                                <button id="setting"><i className="icon-gear" aria-hidden="true"></i> <span>Settings</span></button>
                            </div>
                        </div>
                        <div className="content-wrapper">
                            <div className="content">
                            <CurrentContactView data={currentContact} parent={this} />
                            <MessageListView messages={messages} />
                            <div className="message-input">
                                <div className="wrap">
                                    <input type="text" 
                                        ref="messageInput"  
                                        placeholder="Write your message..." 
                                        onKeyDown={this.addAndSendMessage.bind(this)}  /> 
                                    <i className="icon-attachment attachment" aria-hidden="true"></i>
                                    <button className="submit" 
                                        id="buttonSendMessage" 
                                        onClick={this.addAndSendMessage.bind(this)}>
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
                                © 2017 Bird&nbsp;&nbsp;&nbsp;&bull;&nbsp;&nbsp;&nbsp;Web app kit by <a href="http://followtechnique.com" target="_blank">FollowTechnique</a>.							</div>
                            <div className="pull-right">
                                <div className="label label-info">Version: 1.3.0</div>
                            </div>
                        </div>
                    </div>
                </footer>
                <a id="scrollTop" href="#top"><i className="icon-arrow-up12"></i></a>
                <AddContactView parent={this} />
            </div>  
        );
    }
}

export default MessageView