package com.tvd12.freechat.app.data;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@ToString
@AllArgsConstructor
@EzyObjectBinding(read = false)
public class ChatChannelUsers {

    protected long channelId;
    protected Set<String> users;

    public ChatChannelUsers(long channelId, String user) {
        this(channelId, Sets.newHashSet(user));
    }

    public ChatChannelUsers clone(String user) {
        return new ChatChannelUsers(channelId, user);
    }
}
