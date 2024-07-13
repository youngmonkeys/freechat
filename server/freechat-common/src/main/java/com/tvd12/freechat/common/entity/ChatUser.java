package com.tvd12.freechat.common.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EzyCollection
public class ChatUser {
    @EzyId
    private long id;
    private String username;
    private String password;
    private String fullName;
    private long createdAt;
    private long updatedAt;
}
