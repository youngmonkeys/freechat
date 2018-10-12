package vn.team.freechat.common.creator;

import com.mongodb.MongoClient;
import com.tvd12.ezyfox.function.EzyCreation;
import com.tvd12.ezyfox.mongodb.loader.EzyFileMongoClientLoader;

public class ChatMongoCreator implements EzyCreation<MongoClient> {

	private String filePath;
	
	public ChatMongoCreator filePath(String filePath) {
		this.filePath = filePath;
		return this;
	}

	@Override
	public MongoClient create() {
		return EzyFileMongoClientLoader.load(filePath);
	}
	
}
