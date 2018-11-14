package vn.team.freechat.plugin.testing;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.mongodb.MongoClient;
import com.tvd12.ezyfox.hazelcast.factory.EzySimpleMapTransactionFactory;
import com.tvd12.ezyfox.hazelcast.service.EzyMaxIdService;
import com.tvd12.ezyfox.hazelcast.service.EzySimpleMaxIdService;
import com.tvd12.ezyfox.mongodb.loader.EzyInputStreamMongoClientLoader;
import com.tvd12.ezyfox.morphia.EzyDataStoreBuilder;
import com.tvd12.test.base.BaseTest;

import vn.team.freechat.common.creator.ChatHazelcastCreator;
import xyz.morphia.Datastore;

public abstract class HazelcastBaseTesting extends BaseTest {

	protected static final Datastore DATASTORE;
	protected static final MongoClient MONGO_CLIENT;
	protected static final EzyMaxIdService MAX_ID_SERVICE;
	protected static final HazelcastInstance HZ_INSTANCE;
	
	static {
		MONGO_CLIENT = newMongoClient();
		DATASTORE = newDatastore(MONGO_CLIENT, "test");
		HZ_INSTANCE = newHzInstance();
		MAX_ID_SERVICE = newMaxIdService();
		
		Runtime.getRuntime().addShutdownHook(new Thread(()-> {
		    System.err.println("\n\nshutdown hook, close mongo client\n\n");
			MONGO_CLIENT.close();
		}));
	}
	
	private static MongoClient newMongoClient() {
		return new EzyInputStreamMongoClientLoader()
				.inputStream(getConfigStream())
				.load();
	}
	
	private static EzyMaxIdService newMaxIdService() {
		EzySimpleMaxIdService service = new EzySimpleMaxIdService(HZ_INSTANCE);
		service.setMapTransactionFactory(new EzySimpleMapTransactionFactory(HZ_INSTANCE));
		return service;
	}
	
	private static HazelcastInstance newHzInstance() {
		return new ChatHazelcastCreator()
				.database(MONGO_CLIENT.getDatabase("test"))
				.datastore(DATASTORE)
				.filePath("hazelcast.xml")
				.create();
	}
	
	private static Datastore newDatastore(MongoClient mongoClient, String databaseName) {
    	return EzyDataStoreBuilder.dataStoreBuilder()
    			.mongoClient(mongoClient)
    			.databaseName(databaseName)
    			.scan("vn.team.freechat.common.data")
    			.scan("vn.team.freechat.plugin.data")
    			.build();
    }
	
	private static InputStream getConfigStream() {
		return HazelcastBaseTesting.class.getResourceAsStream("/mongodb_config.properties");
	}
	
	protected Object newServiceBuilder() {
		return null;
	}
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
	
}
