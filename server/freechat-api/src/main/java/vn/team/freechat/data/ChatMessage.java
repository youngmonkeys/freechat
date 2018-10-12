package vn.team.freechat.data;


import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;

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
	@EzyValue("1")
	private long id;
	@EzyValue("6")
	private String verifyId;
	@EzyValue("7")
	private String message;
	@EzyValue("8")
	private Long groupId;
	@EzyValue("9")
	private String sender;
	
	
}
