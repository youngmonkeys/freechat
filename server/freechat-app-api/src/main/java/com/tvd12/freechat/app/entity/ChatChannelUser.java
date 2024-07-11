package com.tvd12.freechat.app.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import com.tvd12.freechat.common.entity.ChatEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EzyCollection
public class ChatChannelUser extends ChatEntity {
    @EzyId
    private ChatChannelUserId id;
}
