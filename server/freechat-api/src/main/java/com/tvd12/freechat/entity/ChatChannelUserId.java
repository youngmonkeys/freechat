package com.tvd12.freechat.entity;

import com.tvd12.ezydata.database.annotation.EzyCollectionId;
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
@EzyCollectionId
@NoArgsConstructor
@AllArgsConstructor
public class ChatChannelUserId {

	private long channelId;
	private String user;
	
	@Override
	public boolean equals(Object obj) {
		return new EzyEquals<ChatChannelUserId>()
				.function(t -> t.channelId)
				.function(t -> t.user)
				.isEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return new EzyHashCodes()
				.append(channelId)
				.append(user)
				.toHashCode();
	}
	
}
