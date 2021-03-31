package com.tvd12.freechat.common.entity;

import com.tvd12.ezydata.database.annotation.EzyCollection;
import com.tvd12.ezyfox.annotation.EzyId;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EzyCollection
public class ChatUser extends ChatEntity {
	
	@EzyId
	private Long id;
	private String username;
	private String password;
	private String firstName = "";
	private String lastName = "";
	private String avatarUrl = "";
	private boolean online;
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
}
