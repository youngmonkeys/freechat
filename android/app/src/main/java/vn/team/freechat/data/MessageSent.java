package vn.team.freechat.data;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;

import java.util.Date;

import vn.team.freechat.contant.MessageType;

/**
 * Created by tavandung12 on 10/7/18.
 */

public class MessageSent extends Message {

    private String to;

    public MessageSent(String message, String to) {
        super(message);
        this.to = to;
        this.sentTime = new Date();
    }

    public String getTo() {
        return to;
    }

    @Override
    public EzyConstant getType() {
        return MessageType.SENT;
    }
}
