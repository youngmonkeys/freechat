package com.tvd12.freechat.common.model;

import lombok.Builder;
import lombok.Getter;

import static com.tvd12.ezyfox.io.EzyStrings.isBlank;

@Getter
@Builder
public class ChatUserModel {
    private long id;
    private String username;
    private String password;
    private String fullName;
    private long createdAt;
    private long updatedAt;

    public String getName() {
        return isBlank(fullName) ? username : fullName;
    }
}
