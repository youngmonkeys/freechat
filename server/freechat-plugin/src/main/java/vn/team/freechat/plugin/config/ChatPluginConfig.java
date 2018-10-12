package vn.team.freechat.plugin.config;

import com.tvd12.properties.file.annotation.Property;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatPluginConfig {

	@Property("database.name")
	private String databaseName;
	
	@Property("mongodb.config.file")
	private String mongodbConfigFile;
	
	@Property("hazelcast.config.file")
	private String hazelcastConfigFile;
	
}
