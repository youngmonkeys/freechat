import * as React from "react";

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
                <li className="reply">
                    <img className="avatar" src={require('../../images/50x50.png')} alt="avatar" />
                    <div className="message">{data.value}</div>
                </li>
            )
            :
            (
                <li className="sent">
                    <div className="message">{data.value}</div>
                    <img className="avatar" src={require('../../images/50x50.png')} alt="avatar" />
                </li>
            )
        );
    }
}

export default MessageItemView;
