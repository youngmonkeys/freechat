package vn.team.freechat.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by tavandung12 on 10/3/18.
 */

public class ContactListItemModel {

    private final long id;
    private String username;
    private String lastMessage;

    private static final AtomicLong COUNTER = new AtomicLong();

    public ContactListItemModel() {
        this("");
    }

    public ContactListItemModel(String username) {
        this(username, "");
    }

    public ContactListItemModel(String username, String lastMessage) {
        this.id = COUNTER.incrementAndGet();
        this.username = username;
        this.lastMessage = lastMessage;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
