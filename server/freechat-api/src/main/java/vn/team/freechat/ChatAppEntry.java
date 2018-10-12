package vn.team.freechat;

import static com.tvd12.ezyfox.util.EzyAutoImplAnnotations.getBeanName;

import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;

import com.hazelcast.core.HazelcastInstance;
import com.mongodb.MongoClient;
import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.core.annotation.EzyServerEventHandler;
import com.tvd12.ezyfox.function.EzyApply;
import com.tvd12.ezyfox.hazelcast.factory.EzyMapTransactionFactory;
import com.tvd12.ezyfox.morphia.EzyDataStoreBuilder;
import com.tvd12.ezyfox.morphia.bean.EzyMorphiaRepositories;
import com.tvd12.ezyfoxserver.command.EzySetup;
import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.context.EzyZoneContext;
import com.tvd12.ezyfoxserver.controller.EzyEventController;
import com.tvd12.ezyfoxserver.ext.EzyAbstractAppEntry;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.ezyfoxserver.support.factory.EzyAppResponseFactory;
import com.tvd12.properties.file.mapping.PropertiesMapper;
import com.tvd12.properties.file.reader.BaseFileReader;

import vn.team.freechat.config.ChatAppConfig;

public class ChatAppEntry extends EzyAbstractAppEntry {

	@Override
	public void config(EzyAppContext ctx) {
		getLogger().info("\n=================== FREE CHAT APP START CONFIG ================\n");
		EzyAppSetting setting = getSetting(ctx);
		String appConfigFile = setting.getConfigFile();
		getLogger().info("\n\n\nappConfigFile = {}\n\n", appConfigFile);
		ChatAppConfig appConfig = readAppConfig(appConfigFile);
		String databaseName = appConfig.getDatabaseName(); 
		
		EzyZoneContext globalContext = ctx.getParent();
		MongoClient mongoClient = globalContext.get(MongoClient.class);
		HazelcastInstance hzInstance = globalContext.get(HazelcastInstance.class);
		EzyMapTransactionFactory mapTransactionFactory = globalContext.get(EzyMapTransactionFactory.class);
		
		Datastore datastore = newDatastore(mongoClient, databaseName);
		
		EzyBeanContext beanContext = createBeanContext(ctx, builder ->  {
			builder.addSingleton("mongoClient", mongoClient);
			builder.addSingleton("datastore", datastore);
			builder.addSingleton("hzInstance", hzInstance);
			builder.addSingleton("hazelcastInstance", hzInstance);
			builder.addSingleton("mapTransactionFactory", mapTransactionFactory);
			addAutoImplMongoRepo(builder, datastore);
		});
		
		addEventControllers(ctx, beanContext);
		
		getLogger().info("\n=================== FREE CHAT APP END CONFIG ================\n");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addEventControllers(EzyAppContext appContext, EzyBeanContext beanContext) {
		EzySetup setup = appContext.get(EzySetup.class);
		List<Object> eventControllers = beanContext.getSingletons(EzyServerEventHandler.class);
		for (Object controller : eventControllers) {
			Class<?> controllerType = controller.getClass();
			EzyServerEventHandler annotation = controllerType.getAnnotation(EzyServerEventHandler.class);
			EzyEventType eventType = EzyEventType.valueOf(annotation.event());
			setup.addEventController(eventType, (EzyEventController) controller);
			getLogger().info("add  event {} controller {}", eventType, controller);
		}
	}

	@Override
	public void start() throws Exception {
		getLogger().info("start free chat app");
	}

	@Override
	public void destroy() {
		getLogger().info("destroy free chat app");
	}

	private EzyBeanContext createBeanContext(
			EzyAppContext context, EzyApply<EzyBeanContextBuilder> applier) {
		EzyBindingContext bindingContext = createBindingContext();
		EzyMarshaller marshaller = bindingContext.newMarshaller();
		EzyUnmarshaller unmarshaller = bindingContext.newUnmarshaller();
		EzyBeanContextBuilder beanContextBuilder = EzyBeanContext.builder()
				.addSingleton("appContext", context)
				.addSingleton("userManager", context.getApp().getUserManager())
				.addSingleton("marshaller", marshaller)
				.addSingleton("unmarshaller", unmarshaller)
				.addSingletonClass(EzyAppResponseFactory.class)
				.scan("vn.team.freechat.repo")
				.scan("vn.team.freechat.controller")
				.scan("vn.team.freechat.handler")
				.scan("vn.team.freechat.config")
				.scan("vn.team.freechat.component")
				.scan("vn.team.freechat.common.repo")
				.scan("vn.team.freechat.common.service")
				.scan("vn.team.freechat.service");
		

		applier.apply(beanContextBuilder);

		return beanContextBuilder.build();

	}
	
	private void addAutoImplMongoRepo(EzyBeanContextBuilder builder, Datastore datastore) {
		Map<Class<?>, Object> additionalRepo = implementMongoRepo(datastore);
		for (Class<?> repoType : additionalRepo.keySet()) {
			builder.addSingleton(getBeanName(repoType), additionalRepo.get(repoType));
		}
	}

	private EzyBindingContext createBindingContext() {
		EzyBindingContext bindingContext = EzyBindingContext.builder()
				.scan("vn.team.freechat")
				.build();
		return bindingContext;
	}

	private Map<Class<?>, Object> implementMongoRepo(Datastore datastore) {
		return EzyMorphiaRepositories.newRepositoriesImplementer()
				.scan("vn.team.freechat.repo")
				.implement(datastore);
	}

	private Datastore newDatastore(MongoClient mongoClient, String databaseName) {
		return EzyDataStoreBuilder.dataStoreBuilder()
				.mongoClient(mongoClient).databaseName(databaseName)
				.scan("vn.team.freechat.data").build();
	}
	
	private ChatAppConfig readAppConfig(String appConfigFile) {
		return new PropertiesMapper()
				.file(appConfigFile)
				.context(getClass())
				.clazz(ChatAppConfig.class)
				.reader(new BaseFileReader())
				.map();
	}
	
	private EzyAppSetting getSetting(EzyAppContext context) {
		return context.getApp().getSetting();
	}

}
