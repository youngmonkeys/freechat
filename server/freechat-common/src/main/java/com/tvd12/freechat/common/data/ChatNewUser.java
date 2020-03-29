package com.tvd12.freechat.common.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatNewUser {

	private ChatUser user;
	private boolean newUser;
	
}
