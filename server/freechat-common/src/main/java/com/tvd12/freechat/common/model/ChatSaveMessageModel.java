package com.tvd12.freechat.common.model;


import com.tvd12.freechat.common.entity.ChatMessageStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatSaveMessageModel {
    private String message;
    private long channelId;
    private long senderId;
    private String clientMessageId;
    private ChatMessageStatus status;
    private long readAt;
}
