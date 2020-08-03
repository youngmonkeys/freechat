/**
 * 
 */
package com.tvd12.freechat.plugin;

import static com.tvd12.ezyfox.util.EzyAutoImplAnnotations.getBeanName;

import java.nio.file.Paths;
import java.util.Map;

import com.hazelcast.core.HazelcastInstance;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.tvd12.ezydata.hazelcast.factory.EzyMapTransactionFactory;
import com.tvd12.ezydata.hazelcast.factory.EzySimpleMapTransactionFactory;
import com.tvd12.ezydata.morphia.EzyDataStoreBuilder;
import com.tvd12.ezydata.morphia.bean.EzyMorphiaRepositoriesImplementer;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.context.EzyServerContext;
import com.tvd12.ezyfoxserver.setting.EzyPluginSetting;
import com.tvd12.ezyfoxserver.support.entry.EzySimplePluginEntry;
import com.tvd12.ezyfoxserver.support.factory.EzyPluginResponseFactory;
import com.tvd12.freechat.common.creator.ChatHazelcastCreator;
import com.tvd12.freechat.common.creator.ChatMongoCreator;
import com.tvd12.freechat.plugin.config.ChatPluginConfig;
import com.tvd12.properties.file.mapping.PropertiesMapper;
import com.tvd12.properties.file.reader.BaseFileReader;

import dev.morphia.Datastore;

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
		String pluginPath = setting.getLocation();
		String pluginConfigFile = setting.getConfigFile();
		ChatPluginConfig pluginConfig = readPluginConfig(pluginConfigFile);
		String databaseName = pluginConfig.getDatabaseName();
		String mongoConfigFile = pluginConfig.getMongodbConfigFile();
		String mongoConfigFilePath = Paths.get(pluginPath, mongoConfigFile).toString();
		MongoClient mongoClient = newMongoClient(mongoConfigFilePath);
		MongoDatabase database = mongoClient.getDatabase(databaseName);
		Datastore datastore = newDatastore(mongoClient, databaseName);
		String hzConfigFile = pluginConfig.getHazelcastConfigFile();
		String hzConfigFilePath = Paths.get(pluginPath, hzConfigFile).toString();
		HazelcastInstance hzInstance = newHazelcast(hzConfigFilePath, database, datastore);
		EzyMapTransactionFactory mapTransactionFactory = new EzySimpleMapTransactionFactory(hzInstance);
		builder.addSingleton("mongoClient", mongoClient);
		builder.addSingleton("datastore", datastore);
		builder.addSingleton("hzInstance", hzInstance);
		builder.addSingleton("hazelcastInstance", hzInstance);
		builder.addSingleton("mapTransactionFactory", mapTransactionFactory);
		addAutoImplMongoRepo(builder, datastore);
		EzyServerContext zoneContext = context.getParent().getParent();
		zoneContext.setProperty(MongoClient.class, mongoClient);
		zoneContext.setProperty(HazelcastInstance.class, hzInstance);
		zoneContext.setProperty(EzyMapTransactionFactory.class, mapTransactionFactory);
		
	}

	@Override
	public void start() throws Exception {
		getLogger().info("chat plugin: start");
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

	private void addAutoImplMongoRepo(EzyBeanContextBuilder builder, Datastore datastore) {
		Map<Class<?>, Object> additionalRepo = implementMongoRepo(datastore);
		for (Class<?> repoType : additionalRepo.keySet()) {
			builder.addSingleton(getBeanName(repoType), additionalRepo.get(repoType));
		}
	}
    
    private Map<Class<?>, Object> implementMongoRepo(Datastore datastore) {
    		return new EzyMorphiaRepositoriesImplementer()
    			.scan("com.tvd12.freechat.common.repo")
    			.scan("com.tvd12.freechat.plugin.repo")
    			.implement(datastore);
    }
    
    private Datastore newDatastore(MongoClient mongoClient, String databaseName) {
    		return EzyDataStoreBuilder.dataStoreBuilder()
    			.mongoClient(mongoClient)
    			.databaseName(databaseName)
    			.scan("com.tvd12.freechat.common.data")
    			.scan("com.tvd12.freechat.plugin.data")
    			.build();
    }

    private ChatPluginConfig readPluginConfig(String appConfigFile) {
		return new PropertiesMapper()
				.file(appConfigFile)
				.context(getClass())
				.clazz(ChatPluginConfig.class)
				.reader(new BaseFileReader())
				.map();
	}
    
    private MongoClient newMongoClient(String filePath) {
		return new ChatMongoCreator()
				.filePath(filePath)
				.create();
	}
	
	private HazelcastInstance newHazelcast(
			String filePath, MongoDatabase database, Datastore datastore) {
		return new ChatHazelcastCreator()
				.filePath(filePath)
				.database(database)
				.datastore(datastore)
				.create();
	}

}