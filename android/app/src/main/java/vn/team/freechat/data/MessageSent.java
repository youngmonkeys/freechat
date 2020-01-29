package vn.team.freechat.data;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;

import vn.team.freechat.constant.MessageType;

/**
 * Created by tavandung12 on 10/7/18.
 */

public class MessageSent extends Message {

    public MessageSent(long channelId, String message) {
        super( channelId, message);
    }

    @Override
    public EzyConstant getType() {
        return MessageType.SENT;
    }
}
