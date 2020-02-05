package vn.team.freechat.model;

import vn.team.freechat.data.ChannelUsers;

/**
 * Created by tavandung12 on 10/3/18.
 */

public class ChatContactModel {

    private final long id;
    private String lastMessage;
    private ChannelUsers contact;

    public ChatContactModel(ChannelUsers contact) {
        this.contact = contact;
        this.lastMessage = "";
        this.id = contact.getChannelId();
    }

    public static ChatContactModel systemModel() {
        return new ChatContactModel(new ChannelUsers(0, "System"));
    }

    public long getId() {
        return id;
    }

    public ChannelUsers getContact() {
        return contact;
    }

    public String getContactUser() {
        return contact.getUser();
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
