package com.tvd12.freechat.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatChannelUserModel {
    private long channelId;
    private long userId;
}
