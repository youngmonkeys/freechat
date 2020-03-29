package com.tvd12.freechat.config;

import com.tvd12.properties.file.annotation.Property;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatAppConfig {

	@Property("database.name")
	private String databaseName;
	
}
