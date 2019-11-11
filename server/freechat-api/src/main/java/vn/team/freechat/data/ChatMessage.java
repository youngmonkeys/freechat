package vn.team.freechat.data;


import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.team.freechat.common.data.ChatData;
import vn.team.freechat.constant.ChatEntities;

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
