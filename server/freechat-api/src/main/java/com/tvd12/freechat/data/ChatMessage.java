package com.tvd12.freechat.data;


import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.freechat.common.data.ChatData;
import com.tvd12.freechat.constant.ChatEntities;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity(value = ChatEntities.CHAT_MESSAGE)
@EzyObjectBinding(subTypeClasses = {ChatData.class})
public class ChatMessage extends ChatData {
	private static final long serialVersionUID = 6130168551127865806L;

	@Id
	private long id;
	private String verifyId;
	private String message;
	private long channelId;
	private String sender;
	
	
}
