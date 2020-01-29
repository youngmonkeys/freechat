package vn.team.freechat.data;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;

import java.util.Date;

import vn.team.freechat.constant.MessageType;

/**
 * Created by tavandung12 on 10/7/18.
 */

public class MessageReceived extends Message {

    private String from;

    public MessageReceived() {
    }

    public MessageReceived(long channelId, String from, String message) {
        super(channelId, message);
        this.from = from;
    }

    public static MessageReceived create(EzyObject data) {
        MessageReceived answer = new MessageReceived();
        answer.deserialize(data);
        return answer;
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
