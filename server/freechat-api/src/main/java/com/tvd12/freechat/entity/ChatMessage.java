package com.tvd12.freechat.entity;


import com.tvd12.ezydata.database.annotation.EzyCollection;
import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.freechat.common.entity.ChatEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EzyCollection
@AllArgsConstructor
@NoArgsConstructor
@EzyObjectBinding(subTypeClasses = {ChatEntity.class})
public class ChatMessage extends ChatEntity {
	@EzyId
	private long id;
	private boolean read;
	private String message;
	private long channelId;
	private String sender;
	private String sentClientMessageId;
	
	
}
