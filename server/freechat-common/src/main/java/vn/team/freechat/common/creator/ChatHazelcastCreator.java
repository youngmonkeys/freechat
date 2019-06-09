package vn.team.freechat.common.creator;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.mongodb.client.MongoDatabase;
import com.tvd12.ezyfox.function.EzyCreation;
import com.tvd12.ezyfox.hazelcast.EzyMongoDatastoreHazelcastFactory;
import com.tvd12.ezyfox.hazelcast.mapstore.EzyMapstoresFetcher;
import com.tvd12.ezyfox.hazelcast.mapstore.EzySimpleMapstoresFetcher;
import com.tvd12.ezyfox.hazelcast.util.EzyHazelcastConfigs;

import dev.morphia.Datastore;

public class ChatHazelcastCreator implements EzyCreation<HazelcastInstance> {

	private String filePath;
	private Datastore datastore;
	private MongoDatabase database;
	
	public ChatHazelcastCreator filePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public ChatHazelcastCreator datastore(Datastore datastore) {
		this.datastore = datastore;
		return this;
	}
	
	public ChatHazelcastCreator database(MongoDatabase database) {
		this.database = database;
		return this;
	}
	
	@Override
	public HazelcastInstance create() {
		EzyMongoDatastoreHazelcastFactory factory = 
				new EzyMongoDatastoreHazelcastFactory();
		factory.setDatabase(database);
		factory.setDatastore(datastore);
		factory.setMapstoresFetcher(newMapstoresFetcher());
		return factory.newHazelcast(newConfig());
	}
	
	private Config newConfig() {
		return EzyHazelcastConfigs.newXmlConfigBuilder(filePath);
	}
	
	private EzyMapstoresFetcher newMapstoresFetcher() {
		return EzySimpleMapstoresFetcher.builder()
				.scan("vn.team.freechat.mapstore")
				.scan("vn.team.freechat.common.mapstore")
				.scan("vn.team.freechat.plugin.mapstore")
				.build();
	}
	
}
