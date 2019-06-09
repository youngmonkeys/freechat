package vn.team.freechat.common.data;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.team.freechat.common.constant.ChatEntities;

@Setter
@Getter
@ToString
@EzyObjectBinding(
		read = false, 
		accessType = EzyAccessType.NONE,
		subTypes = false)
@Entity(value = ChatEntities.CHAT_USER, noClassnameStored = true)
public class ChatUser extends ChatData {
	private static final long serialVersionUID = 6130168551127890806L;
	
	@Id
	private Long id;
	@EzyValue
	private String username;
	private String password;
	private String firstName = "";
	private String lastName = "";
	private String avatarUrl = "";
	private boolean online;
	
	@EzyValue
	public String getFullName() {
		return firstName + " " + lastName;
	}
}
