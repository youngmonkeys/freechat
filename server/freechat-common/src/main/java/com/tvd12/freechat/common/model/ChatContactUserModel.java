package com.tvd12.freechat.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatContactUserModel {
    private ChatUserModel user;
    private long channelId;
}
