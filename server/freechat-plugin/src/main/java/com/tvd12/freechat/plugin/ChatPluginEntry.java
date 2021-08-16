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
import com.tvd12.properties.file.util.PropertiesUtil;

/**
 * @author tavandung12
 *
 */
public class ChatPluginEntry extends EzySimplePluginEntry {

	@Override
	protected void preConfig(EzyPluginContext ctx) {
		logger.info("\n=================== FREE CHAT PLUGIN START CONFIG ================\n");
	}
	
	@Override
	protected void postConfig(EzyPluginContext ctx) {
		logger.info("\n=================== FREE CHAT PLUGIN END CONFIG ================\n");
	}
	
	@Override
	protected void setupBeanContext(EzyPluginContext context, EzyBeanContextBuilder builder) {
		EzyPluginSetting setting = context.getPlugin().getSetting();
		builder.addProperties(getConfigFile(setting));
		Properties properties = builder.getProperties();
		Properties mongoProperties = PropertiesUtil.filterPropertiesByKeyPrefix(
				properties, 
				EzyMongoClientLoader.PROPERTY_NAME_PREFIX);
		MongoClient mongoClient = newMongoClient(mongoProperties);
		EzyDatabaseContext databaseContext = newDatabaseContext(
				mongoClient,
				mongoProperties
		);
        Map<String, Object> repos = databaseContext.getRepositoriesByName();
        for(String repoName : repos.keySet())
        	builder.addSingleton(repoName, repos.get(repoName));
		EzyZoneContext zoneContext = context.getParent();
		zoneContext.setProperty(MongoClient.class, mongoClient);
		zoneContext.setProperty("mongoProperties", mongoProperties);
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