import * as React from "react";
import MessageItemView from "./MessageItemView";

class MessageListView extends React.Component {
    constructor(props) {
        super(props);
        this.messagesEndRef = React.createRef();
    }

    componentDidUpdate() {
        this.scrollToBottom();
    }

    scrollToBottom() {
        this.messagesEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }

    render()  {
        const {messages} = this.props;
        return (
            <div className="messages">
                <ul>
                    {
                        messages.map((msg, i) => <MessageItemView key={i} data={msg} />)
                    }
                </ul>
                <div ref={this.messagesEndRef} />
            </div>
        );
    }
}

export default MessageListView;