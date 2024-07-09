import * as React from "react";
import Mvc from "mvc-es6";

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
            <div className="current-contact">
                <img className="avatar" src={require('../../images/70x70.png')} alt="avatar" />
                <span>{channel.users[0]}</span>
            </div>
        );
    }
}

export default CurrentContactView;
