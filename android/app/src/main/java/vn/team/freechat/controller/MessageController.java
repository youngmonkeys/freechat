package vn.team.freechat.controller;

import com.tvd12.ezyfoxserver.client.entity.EzyObject;

import vn.team.freechat.data.MessageReceived;
import vn.team.freechat.view.MessageView;

/**
 * Created by tavandung12 on 10/7/18.
 */

public class MessageController {

    private MessageView messageView;

    public void handReceivedMessage(EzyObject data) {
        MessageReceived message = new MessageReceived();
        message.deserialize(data);
        messageView.addMessage(message);
    }

    public void setMessageView(MessageView messageView) {
        this.messageView = messageView;
    }
}
