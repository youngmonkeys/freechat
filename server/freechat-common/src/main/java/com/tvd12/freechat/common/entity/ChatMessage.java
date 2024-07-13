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
public class ChatMessage {
    @EzyId
    private long id;
    private String message;
    private long channelId;
    private long senderId;
    private String clientMessageId;
    private ChatMessageStatus status;
    private long sentAt;
    private long readAt;
    private long updatedAt;
}
