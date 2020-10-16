package com.tvd12.freechat.common.data;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.freechat.common.constant.ChatEntities;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EzyObjectBinding(
		read = false,
		accessType = EzyAccessType.NONE,
		subTypes = false)
@Entity(value = ChatEntities.CHAT_USER_TOKEN, noClassnameStored = true)
public class ChatUserToken extends ChatData {

	@Id
	private String token;
	private String user;
}
