package vn.team.freechat.model;

import vn.team.freechat.data.ChannelUsers;

/**
 * Created by tavandung12 on 10/3/18.
 */

public class SearchContactModel {

    private String username;
    private String fullName;

    public SearchContactModel(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }
}
