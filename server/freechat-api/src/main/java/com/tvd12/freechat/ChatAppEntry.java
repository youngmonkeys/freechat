package com.tvd12.freechat;

import java.util.Map;
import java.util.Properties;

import com.mongodb.MongoClient;
import com.tvd12.ezydata.database.EzyDatabaseContext;
import com.tvd12.ezydata.mongodb.EzyMongoDatabaseContextBuilder;
import com.tvd12.ezydata.mongodb.loader.EzyMongoClientLoader;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.context.EzyZoneContext;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.ezyfoxserver.support.entry.EzySimpleAppEntry;

public class ChatAppEntry extends EzySimpleAppEntry {

	@Override
	protected void preConfig(EzyAppContext ctx) {
		getLogger().info("\n=================== FREE CHAT APP START CONFIG ================\n");
	}
	
	@Override
	protected void postConfig(EzyAppContext ctx) {
		getLogger().info("\n=================== FREE CHAT APP END CONFIG ================\n");
	}
	
	@Override
	protected void setupBeanContext(EzyAppContext context, EzyBeanContextBuilder builder) {
		EzyZoneContext zoneContext = context.getParent();
		Properties mongoProperties = zoneContext.getProperty("mongoProperties");
		MongoClient mongoClient = zoneContext.get(MongoClient.class);
		EzyDatabaseContext databaseContext = newDatabaseContext(
				mongoClient,
				mongoProperties
		);
        Map<String, Object> repos = databaseContext.getRepositoriesByName();
        for(String repoName : repos.keySet())
        	builder.addSingleton(repoName, repos.get(repoName));
	}
	
	protected String getConfigFile(EzyAppSetting setting) {
		return setting.getConfigFile();
	}
	
	public void start() throws Exception {
		getLogger().info("start free chat app");
	}

	@Override
	protected String[] getScanableBeanPackages() {
		return new String[] {
				"com.tvd12.freechat.repo",
				"com.tvd12.freechat.controller",
				"com.tvd12.freechat.handler",
				"com.tvd12.freechat.config",
				"com.tvd12.freechat.component",
				"com.tvd12.freechat.common.repo",
				"com.tvd12.freechat.common.service",
				"com.tvd12.freechat.service"
		};
	}
	
	@Override
	protected String[] getScanableBindingPackages() {
		return new String[] {
			"com.tvd12.freechat"
		};
	}
	
	private EzyDatabaseContext newDatabaseContext(
			MongoClient mongoClient,
			Properties properties) {
		String databaseName = properties.getProperty(EzyMongoClientLoader.DATABASE);
        return new EzyMongoDatabaseContextBuilder()
                .properties(properties)
                .mongoClient(mongoClient)
                .databaseName(databaseName)
                .scan("com.tvd12.freechat.common.entity")
                .scan("com.tvd12.freechat.common.repo")
                .scan("com.tvd12.freechat.entity")
                .scan("com.tvd12.freechat.repo")
                .build();
    }
}
