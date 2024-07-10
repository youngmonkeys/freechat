import * as React from "react";
import SocketProxy from '../../socket/SocketProxy'

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
            <div className="profile-wrapper">
                <div className="profile">
                    <a href="#">
                        <img src={require('../../images/80x80.png')}
                             className="avatar online" alt="profile" />
                    </a>
                    <span id ="userName" className="username">
                        {data.username}
                    </span>
                </div>
            </div>
        );
    }
}

export default MyShortProfileView;