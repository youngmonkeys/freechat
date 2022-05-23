package com.tvd12.freechat.test.entity;


import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import com.tvd12.freechat.common.entity.ChatEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@ToString
@Entity(name = "message")
@EzyCollection
@AllArgsConstructor
@NoArgsConstructor
@EzyObjectBinding(subTypeClasses = {ChatEntity.class})
public class ChatMessage {
    @Id
    @EzyId
    private long id;
    @Column(name = "isRead")
    private boolean read;
    private String message;
    private long channelId;
    private String sender;
    private String sentClientMessageId;
}
