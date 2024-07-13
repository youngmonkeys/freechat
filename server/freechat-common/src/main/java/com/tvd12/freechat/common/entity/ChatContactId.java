package com.tvd12.freechat.common.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import lombok.*;

@Setter
@Getter
@ToString
@EzyId
@NoArgsConstructor
@EqualsAndHashCode
public class ChatContactId {
    private long user1stId;
    private long user2ndId;

    public ChatContactId(
        long user1stId,
        long user2ndId
    ) {
        if (user1stId <= user2ndId) {
            this.user1stId = user1stId;
            this.user2ndId = user2ndId;
        } else {
            this.user1stId = user2ndId;
            this.user2ndId = user1stId;
        }
    }
}
