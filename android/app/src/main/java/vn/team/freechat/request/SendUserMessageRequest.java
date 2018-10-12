package vn.team.freechat.request;

import com.tvd12.ezyfoxserver.client.entity.EzyData;
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;

import vn.team.freechat.contant.Commands;

/**
 * Created by tavandung12 on 10/6/18.
 */

public class SendUserMessageRequest implements EzyRequest {

    private final String to;
    private final String message;

    public SendUserMessageRequest(String to, String message) {
        this.to = to;
        this.message = message;
    }

    @Override
    public EzyData serialize() {
        return EzyEntityFactory.newObjectBuilder()
                .append("to", to)
                .append("message", message)
                .build();
    }

    @Override
    public Object getCommand() {
        return Commands.CHAT_USER_MESSAGE;
    }
}
