package vn.team.freechat.request;

import com.tvd12.ezyfoxserver.client.entity.EzyData;
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;

import vn.team.freechat.constant.Commands;

/**
 * Created by tavandung12 on 10/6/18.
 */

public class SendUserMessageRequest implements EzyRequest {

    private final long channelId;
    private final String message;

    public SendUserMessageRequest(long channelId, String message) {
        this.message = message;
        this.channelId = channelId;
    }

    @Override
    public EzyData serialize() {
        return EzyEntityFactory.newObjectBuilder()
                .append("channelId", channelId)
                .append("message", message)
                .build();
    }

    @Override
    public Object getCommand() {
        return Commands.CHAT_USER_MESSAGE;
    }
}
