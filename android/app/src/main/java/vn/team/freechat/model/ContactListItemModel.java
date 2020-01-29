package vn.team.freechat.model;

import java.util.concurrent.atomic.AtomicLong;

import vn.team.freechat.data.ChannelUsers;

/**
 * Created by tavandung12 on 10/3/18.
 */

public class ContactListItemModel {

    private final long id;
    private String lastMessage;
    private ChannelUsers contact;

    public ContactListItemModel(ChannelUsers contact) {
        this.contact = contact;
        this.lastMessage = "";
        this.id = contact.getChannelId();
    }

    public static ContactListItemModel systemModel() {
        return new ContactListItemModel(new ChannelUsers(0, "System"));
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
