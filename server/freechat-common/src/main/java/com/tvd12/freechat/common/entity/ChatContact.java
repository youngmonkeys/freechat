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
public class ChatContact {
    @EzyId
    private ChatContactId id;
    private long channelId;
    private long createdAt;
    private long updatedAt;
}
