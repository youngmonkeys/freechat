package vn.team.freechat.data;

import com.tvd12.ezyfoxserver.client.codec.EzyObjectDeserializable;
import com.tvd12.ezyfoxserver.client.constant.EzyConstant;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tavandung12 on 10/7/18.
 */

public abstract class Message implements EzyObjectDeserializable {

    protected String message;
    protected Date sentTime;

    public Message() {
    }

    public Message(String message) {
        this(message, new Date());
    }

    public Message(String message, Date sentTime) {
        this.message = message;
        this.sentTime = sentTime;
    }

    @Override
    public void deserialize(EzyObject data) {
        this.message = data.get("message");
    }

    public String getMessage() {
        return message;
    }

    public abstract EzyConstant getType();

    public String getSentTimeString() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        String answer = format.format(sentTime);
        return answer;
    }
}
