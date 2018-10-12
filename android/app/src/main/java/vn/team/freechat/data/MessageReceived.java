package vn.team.freechat.data;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;

import java.util.Date;

import vn.team.freechat.contant.MessageType;

/**
 * Created by tavandung12 on 10/7/18.
 */

public class MessageReceived extends Message {

    private String from;

    public MessageReceived() {
    }

    public MessageReceived(String message, String from) {
        super(message);
        this.from = from;
    }

    @Override
    public void deserialize(EzyObject data) {
        super.deserialize(data);
        this.from = data.get("from");
        this.sentTime = new Date();
    }

    public String getFrom() {
        return from;
    }

    @Override
    public EzyConstant getType() {
        return MessageType.RECEIVED;
    }
}
