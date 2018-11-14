package vn.team.freechat.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.team.freechat.common.data.ChatData;
import vn.team.freechat.constant.ChatEntities;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;

@Setter
@Getter
@ToString
@Entity(value = ChatEntities.CHAT_CONTACT, noClassnameStored = true)
public class ChatContact extends ChatData {
	private static final long serialVersionUID = -3697357887805554571L;

	@Id
	private ChatContactId id;
	private String actor;
	
}
