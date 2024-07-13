package com.tvd12.freechat.test.entity;


import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import lombok.*;

@Setter
@Getter
@ToString
@EzyCollection
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @EzyId
    private long id;
    private boolean read;
    private String message;
    private long channelId;
    private String sender;
    private String sentClientMessageId;
}
