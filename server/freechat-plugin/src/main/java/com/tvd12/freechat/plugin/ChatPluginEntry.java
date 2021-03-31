/**
 * 
 */
package com.tvd12.freechat.plugin;

import java.util.Map;
import java.util.Properties;

import com.mongodb.MongoClient;
import com.tvd12.ezydata.database.EzyDatabaseContext;
import com.tvd12.ezydata.mongodb.EzyMongoDatabaseContextBuilder;
import com.tvd12.ezydata.mongodb.loader.EzyMongoClientLoader;
import com.tvd12.ezydata.mongodb.loader.EzySimpleMongoClientLoader;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.context.EzyZoneContext;
import com.tvd12.ezyfoxserver.setting.EzyPluginSetting;
import com.tvd12.ezyfoxserver.support.entry.EzySimplePluginEntry;
import com.tvd12.ezyfoxserver.support.factory.EzyPluginResponseFactory;
import com.tvd12.properties.file.reader.BaseFileReader;

/**
 * @author tavandung12
 *
 */
public class ChatPluginEntry extends EzySimplePluginEntry {

	@Override
	protected void preConfig(EzyPluginContext ctx) {
		getLogger().info("\n=================== FREE CHAT PLUGIN START CONFIG ================\n");
	}
	
	@Override
	protected void postConfig(EzyPluginContext ctx) {
		getLogger().info("\n=================== FREE CHAT PLUGIN END CONFIG ================\n");
	}
	
	@Override
	protected void setupBeanContext(EzyPluginContext context, EzyBeanContextBuilder builder) {
		EzyPluginSetting setting = context.getPlugin().getSetting();
		String pluginConfigFile = getConfigFile(setting);
		Properties properties = new BaseFileReader().read(pluginConfigFile);
		MongoClient mongoClient = newMongoClient(properties);
		EzyDatabaseContext databaseContext = newDatabaseContext(
				mongoClient,
				properties
		);
        Map<String, Object> repos = databaseContext.getRepositoriesByName();
        for(String repoName : repos.keySet())
        	builder.addSingleton(repoName, repos.get(repoName));
		EzyZoneContext zoneContext = context.getParent();
		zoneContext.setProperty(MongoClient.class, mongoClient);
		zoneContext.setProperty("mongoProperties", properties);
	}
	
	protected String getPluginPath(EzyPluginSetting setting) {
		return setting.getLocation();
	}
	
	protected String getConfigFile(EzyPluginSetting setting) {
		return setting.getConfigFile();
	}

	@Override
	public void start() throws Exception {
		logger.info("chat plugin: start");
	}
	
	@Override
	protected String[] getScanableBeanPackages() {
		return new String[] {
			"com.tvd12.freechat.plugin",
			"com.tvd12.freechat.common.repo",
			"com.tvd12.freechat.common.service"
		};
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class[] getSingletonClasses() {
		return new Class[] {
			EzyPluginResponseFactory.class
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
                .build();
    }

    private MongoClient newMongoClient(Properties properties) {
        return EzySimpleMongoClientLoader.load(properties);
    }

}