package vn.team.freechat.data;

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
@Entity(value = ChatEntities.CHAT_CHANNEL, noClassnameStored = true)
public class ChatChannel extends ChatData {
	private static final long serialVersionUID = -3697357887805554571L;

	@Id
	private long id;
	private String creator;
	
}
