package com.tvd12.freechat.data;

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
@Entity(value = ChatEntities.CHAT_CHANNEL, noClassnameStored = true)
public class ChatChannel extends ChatData {
	private static final long serialVersionUID = -3697357887805554571L;

	@Id
	private long id;
	private String creator;
	
}
