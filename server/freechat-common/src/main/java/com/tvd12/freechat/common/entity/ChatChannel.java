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
public class ChatChannel {
    @EzyId
    private long id;
    private long creatorUserId;
    private String name;
    private ChatChannelType type;
    private long createdAt;
    private long updatedAt;
    private long lastChattedAt;
}
