package com.tvd12.freechat.data;

import com.tvd12.ezyfox.util.EzyEquals;
import com.tvd12.ezyfox.util.EzyHashCodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatContactId {

	private String user1st;
	private String user2nd;
	
	@Override
	public boolean equals(Object obj) {
		return new EzyEquals<ChatContactId>()
				.function(t -> t.user1st)
				.function(t -> t.user2nd)
				.isEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return new EzyHashCodes()
				.append(user1st)
				.append(user2nd)
				.toHashCode();
	}
	
}
