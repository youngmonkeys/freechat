import * as React from "react";
import Mvc from "mvc-es6";

class ContactView extends React.Component {
    constructor(props) {
        super(props);
        this.parent = props.parent;
        this.data = props.data;
        this.onClick = this.onClick.bind(this);
        this.mvc = Mvc.getInstance();
        this.chatController = this.mvc.getController("chat");
        this.chatModel = this.mvc.models.chat;
        if (!this.data.channel.channelId) {
            this.chatModel.currentContactView = this;
        }
        this.state = {selected :  this.chatModel.currentContactView == this};
    }

    unselect() {
        this.setState({selected : false});
    }

    onClick() {
        const currentContactView = this.chatModel.currentContactView;
        console.log(currentContactView);
        if(currentContactView == this) {
            return;
        }
        if(currentContactView != null) {
            currentContactView.unselect();
        }
        this.chatModel.currentContactView = this;
        let channelId = this.data.channel.channelId;
        let {selected} = this.state;
        let newSelected = !selected;
        this.setState({selected : newSelected});
        this.chatController.updateViews("changeTarget", channelId);
    }

    render() {
        const {data} = this.props;
        const {selected} = this.state;
        return (
            <li className={"contact" + (selected ? " active" : "")} onClick={this.onClick}>
                <img className="avatar" src={require('../../images/70x70.png')} alt="avatar" />
                <div className="meta">
                    <span className="name">{data.channel.users[0]}</span>
                    <span className="preview">{data.lastMessage}</span>
                </div>
            </li>
        );
    }
}

export default ContactView;