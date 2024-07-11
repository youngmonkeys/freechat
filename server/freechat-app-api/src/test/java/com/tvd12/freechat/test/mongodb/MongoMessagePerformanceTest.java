package com.tvd12.freechat.test.mongodb;

import com.tvd12.ezydata.database.EzyDatabaseContext;
import com.tvd12.ezydata.mongodb.EzyMongoDatabaseContextBuilder;
import com.tvd12.ezydata.mongodb.loader.EzyMongoClientLoader;
import com.tvd12.ezydata.mongodb.loader.EzySimpleMongoClientLoader;
import com.tvd12.freechat.test.AbstractMessagePerformanceTest;
import com.tvd12.freechat.test.entity.ChatMessage;

import java.util.List;

public class MongoMessagePerformanceTest extends AbstractMessagePerformanceTest {

    public static void main(String[] args) {
        MongoMessageRepository repo = getMessageRepository();
        List<List<ChatMessage>> messageLists = prepareMessages();
        long start = System.currentTimeMillis();
        for (List<ChatMessage> messageList : messageLists) {
            for (ChatMessage message : messageList) {
                repo.save(message);
            }
        }
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println(elapsedTime);
    }

    private static MongoMessageRepository getMessageRepository() {
        String databaseName = properties.getProperty(EzyMongoClientLoader.DATABASE);
        EzyDatabaseContext context = new EzyMongoDatabaseContextBuilder()
            .properties(properties)
            .mongoClient(EzySimpleMongoClientLoader.load(properties))
            .databaseName(databaseName)
            .scan("com.tvd12.freechat.test.entity")
            .scan("com.tvd12.freechat.test.mongodb")
            .build();
        return context.getRepository(MongoMessageRepository.class);
    }
}
