package com.tvd12.freechat.common.result;

import com.tvd12.ezyfox.database.annotation.EzyQueryResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EzyQueryResult
public class ChatUserChannelResult {
    private long channelId;
    private long userId;
}
