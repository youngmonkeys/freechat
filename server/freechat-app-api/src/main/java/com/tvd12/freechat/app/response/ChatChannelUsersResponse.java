package com.tvd12.freechat.app.response;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
@EzyObjectBinding(read = false)
public class ChatChannelUsersResponse {

    protected long channelId;
    protected Collection<String> users;

    public ChatChannelUsersResponse(long channelId, String user) {
        this(channelId, Collections.singleton(user));
    }

    public ChatChannelUsersResponse clone(String user) {
        return new ChatChannelUsersResponse(channelId, user);
    }
}
