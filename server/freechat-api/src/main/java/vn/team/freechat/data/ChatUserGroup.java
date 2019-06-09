package vn.team.freechat.data;

import java.io.Serializable;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.team.freechat.common.data.ChatData;
import vn.team.freechat.constant.ChatEntities;

@Setter
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity(value = ChatEntities.CHAT_USER_GROUP, noClassnameStored = true)
public class ChatUserGroup extends ChatData {
	private static final long serialVersionUID = 1312646284225392802L;

	@Id
	private DataId id;

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode(of = {"groupId", "username"})
	public static class DataId implements Serializable {
		private static final long serialVersionUID = -4608348140465344148L;
		
		private long groupId;
		private String username;
	}
	
}
