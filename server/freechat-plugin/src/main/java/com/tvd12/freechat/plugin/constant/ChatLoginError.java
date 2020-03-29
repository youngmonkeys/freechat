package com.tvd12.freechat.plugin.constant;

import com.tvd12.ezyfoxserver.constant.EzyILoginError;

import lombok.Getter;

@Getter
public enum ChatLoginError implements EzyILoginError {

	ALREADY_REGISTER(10, "account already register");
	
	private final int id;
	private final String message;
	
	private ChatLoginError(int id, String message) {
		this.id = id;
		this.message = message;
	}

}
