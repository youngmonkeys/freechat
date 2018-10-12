package vn.team.freechat.controller;

import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;

import vn.team.freechat.view.ContactView;

/**
 * Created by tavandung12 on 10/7/18.
 */

public class ContactController {

    private ContactView contactView;

    public void handleGetContactsResponse(EzyObject data) {
        EzyArray usernames = data.get("contacts");
        contactView.addContacts(usernames);
    }

    public void setContactView(ContactView contactView) {
        this.contactView = contactView;
    }
}
