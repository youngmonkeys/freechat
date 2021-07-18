package com.tvd12.freechat.common.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import lombok.*;

@Setter
@Getter
@ToString
@EzyCollection
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserFirebaseToken {
    @EzyId
    private String firebaseToken;
    private String username;
}