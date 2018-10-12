package vn.team.freechat.handler;

import com.tvd12.ezyfoxserver.client.handler.EzyHandshakeHandler;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;

/**
 * Created by tavandung12 on 10/1/18.
 */

public class HandshakeHandler extends EzyHandshakeHandler {

    private final EzyRequest loginRequest;

    public HandshakeHandler(EzyRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    @Override
    public EzyRequest getLoginRequest() {
        return loginRequest;
    }
}
