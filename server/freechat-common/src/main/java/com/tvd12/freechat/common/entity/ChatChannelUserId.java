package com.tvd12.freechat.common.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import lombok.*;

@Setter
@Getter
@ToString
@EzyId
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChatChannelUserId {
    private long channelId;
    private long userId;
}
