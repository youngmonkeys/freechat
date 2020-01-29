package vn.team.freechat.constant;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant;

/**
 * Created by tavandung12 on 10/5/18.
 */

public enum MessageType implements EzyConstant {
    SENDING(1),
    SENT(2),
    RECEIVED(3);

    private final int id;

    private MessageType(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return toString();
    }
}
