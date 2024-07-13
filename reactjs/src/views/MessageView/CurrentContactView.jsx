import * as React from "react";
import Mvc from "mvc-es6";

class CurrentContactView extends React.Component {
    constructor(props) {
        super(props);
        this.mvc = Mvc.getInstance();
        this.chatController = this.mvc.getController("chat");
        this.state = {
            channel: this.mvc.models.contacts[0].channel
        };
    }

    componentDidMount() {
        this.chatController.addView("changeTarget", 'profile', (target) => {
            let channel = this.mvc.models.contactDict[target].channel;
            console.log("chat profile now is: " + JSON.stringify(channel));
            this.setState({channel: channel});
        });
    }

    componentWillUnmount() {
        this.chatController.removeView("changeTarget", 'profile');
    }

    render() {
        const {channel} = this.state;
        return channel ? (
            <div className="current-contact">
                <img className="avatar" src={require('../../images/70x70.png')} alt="avatar" />
                <span>{channel.users[0]}</span>
            </div>
        ) : null;
    }
}

export default CurrentContactView;
