import * as React from "react";
import Mvc from "mvc-es6";

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
                <img className="avatar" src={require('../../images/70x70.png')} alt="avatar" />
                <div className="meta">
                    <p className="name">{data.channel.users[0]}</p>
                    <p className="preview">{data.lastMessage}</p>
                </div>
            </li>
        );
    }
}

export default ContactView;