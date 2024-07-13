package com.tvd12.freechat.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatContactModel {
    private long user1stId;
    private long user2ndId;
    private long channelId;
    private long createdAt;
    private long updatedAt;
}
