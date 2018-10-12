package vn.team.freechat.request;

import com.tvd12.ezyfoxserver.client.entity.EzyData;
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;

import vn.team.freechat.contant.Commands;

/**
 * Created by tavandung12 on 10/5/18.
 */

public class SendSystemMessageRequest implements EzyRequest {

    private final String message;

    public SendSystemMessageRequest(String message) {
        this.message = message;
    }

    @Override
    public EzyData serialize() {
        return EzyEntityFactory.newObjectBuilder()
                .append("message", message)
                .build();
    }

    @Override
    public Object getCommand() {
        return Commands.CHAT_SYSTEM_MESSAGE;
    }
}
