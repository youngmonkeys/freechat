package vn.team.freechat.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChannelUsers implements Serializable {

    protected final long channelId;
    protected final List<String> users;

    public ChannelUsers(long channelId, String... users) {
        this(channelId, Arrays.asList(users));
    }

    public ChannelUsers(long channelId, List<String> users) {
        this.channelId = channelId;
        this.users = new ArrayList<>();
        this.users.addAll(users);
    }

    public String getUser() {
        return users.get(0);
    }

    public long getChannelId() {
        return channelId;
    }

    public List<String> getUsers() {
        return users;
    }
}
