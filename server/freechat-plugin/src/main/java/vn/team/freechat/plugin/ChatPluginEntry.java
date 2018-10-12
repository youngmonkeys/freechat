/**
 * 
 */
package vn.team.freechat.plugin;

import static com.tvd12.ezyfox.util.EzyAutoImplAnnotations.getBeanName;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;

import com.hazelcast.core.HazelcastInstance;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.core.annotation.EzyServerEventHandler;
import com.tvd12.ezyfox.core.util.EzyServerEventHandlerAnnotations;
import com.tvd12.ezyfox.function.EzyApply;
import com.tvd12.ezyfox.hazelcast.factory.EzyMapTransactionFactory;
import com.tvd12.ezyfox.hazelcast.factory.EzySimpleMapTransactionFactory;
import com.tvd12.ezyfox.morphia.EzyDataStoreBuilder;
import com.tvd12.ezyfox.morphia.bean.EzyMorphiaRepositories;
import com.tvd12.ezyfoxserver.command.EzySetup;
import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.context.EzyServerContext;
import com.tvd12.ezyfoxserver.controller.EzyEventController;
import com.tvd12.ezyfoxserver.ext.EzyAbstractPluginEntry;
import com.tvd12.ezyfoxserver.setting.EzyPluginSetting;
import com.tvd12.ezyfoxserver.support.factory.EzyPluginResponseFactory;
import com.tvd12.properties.file.mapping.PropertiesMapper;
import com.tvd12.properties.file.reader.BaseFileReader;

import vn.team.freechat.common.creator.ChatHazelcastCreator;
import vn.team.freechat.common.creator.ChatMongoCreator;
import vn.team.freechat.plugin.config.ChatPluginConfig;

/**
 * @author tavandung12
 *
 */
public class ChatPluginEntry extends EzyAbstractPluginEntry {

	@Override
	public void config(EzyPluginContext ctx) {
		getLogger().info("\n=================== FREE CHAT PLUGIN START CONFIG ================\n");
		EzyPluginSetting setting = getSetting(ctx);
		String pluginPath = setting.getLocation();
		String pluginConfigFile = setting.getConfigFile();
		getLogger().info("\n\n\nappConfigFile = {}\n\n", pluginConfigFile);
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
		
		EzyBeanContext beanContext = createBeanContext(ctx, builder ->  {
			builder.addSingleton("mongoClient", mongoClient);
			builder.addSingleton("datastore", datastore);
			builder.addSingleton("hzInstance", hzInstance);
			builder.addSingleton("hazelcastInstance", hzInstance);
			builder.addSingleton("mapTransactionFactory", mapTransactionFactory);
			addAutoImplMongoRepo(builder, datastore);
		});
		
		EzyServerContext globalContext = ctx.getParent().getParent();
		globalContext.setProperty(MongoClient.class, mongoClient);
		globalContext.setProperty(HazelcastInstance.class, hzInstance);
		globalContext.setProperty(EzyMapTransactionFactory.class, mapTransactionFactory);
		
		addEventHandlers(ctx, beanContext);
		getLogger().info("\n=================== FREE CHAT PLUGIN END CONFIG ================\n");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addEventHandlers(EzyPluginContext context, EzyBeanContext beanContext) {
		EzySetup setup = context.get(EzySetup.class);
		List<Object> eventHandlers = beanContext.getSingletons(EzyServerEventHandler.class);
		for(Object handler : eventHandlers) {
			Class<?> handlerType = handler.getClass();
			EzyServerEventHandler annotation = handlerType.getAnnotation(EzyServerEventHandler.class);
			String eventName = EzyServerEventHandlerAnnotations.getEvent(annotation);
			setup.addEventController(EzyEventType.valueOf(eventName), (EzyEventController) handler);
		}
	}

	@Override
	public void start() throws Exception {
		getLogger().info("chat plugin: start");
	}

	@Override
	public void destroy() {
		getLogger().info("auth plugin: destry");
	}
	
	private EzyBeanContext createBeanContext(
			EzyPluginContext context, EzyApply<EzyBeanContextBuilder> applier) {
    	EzyBindingContext bindingContext = createBindingContext();
    	EzyMarshaller marshaller = bindingContext.newMarshaller();
    	EzyUnmarshaller unmarshaller = bindingContext.newUnmarshaller();
    	EzyBeanContextBuilder beanContextBuilder = EzyBeanContext.builder()
    			.addSingleton("pluginContext", context)
    			.addSingleton("marshaller", marshaller)
    			.addSingleton("unmarshaller", unmarshaller)
    			.addSingletonClass(EzyPluginResponseFactory.class)
    			.scan("vn.team.freechat.plugin")
    			.scan("vn.team.freechat.common.repo")
    			.scan("vn.team.freechat.common.service");
    	
    	applier.apply(beanContextBuilder);

		return beanContextBuilder.build();
    }
	
	private void addAutoImplMongoRepo(EzyBeanContextBuilder builder, Datastore datastore) {
		Map<Class<?>, Object> additionalRepo = implementMongoRepo(datastore);
		for (Class<?> repoType : additionalRepo.keySet()) {
			builder.addSingleton(getBeanName(repoType), additionalRepo.get(repoType));
		}
	}
    
    private Map<Class<?>, Object> implementMongoRepo(Datastore datastore) {
    	return EzyMorphiaRepositories.newRepositoriesImplementer()
    			.scan("vn.team.freechat.common.repo")
    			.scan("vn.team.freechat.plugin.repo")
    			.implement(datastore);
    }
    
    private Datastore newDatastore(MongoClient mongoClient, String databaseName) {
    		return EzyDataStoreBuilder.dataStoreBuilder()
    			.mongoClient(mongoClient)
    			.databaseName(databaseName)
    			.scan("vn.team.freechat.common.data")
    			.scan("vn.team.freechat.plugin.data")
    			.build();
    }
    
    private EzyBindingContext createBindingContext() {
    		EzyBindingContext bindingContext = EzyBindingContext.builder()
    			.scan("vn.team.freechat.plugin")
    			.build(); 
    		return bindingContext;
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
    
    protected EzyPluginSetting getSetting(EzyPluginContext context) {
    		return context.getPlugin().getSetting();
    }

}