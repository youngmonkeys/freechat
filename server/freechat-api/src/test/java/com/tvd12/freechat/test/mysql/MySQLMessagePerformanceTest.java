package com.tvd12.freechat.test.mysql;

import com.tvd12.ezydata.database.EzyDatabaseContext;
import com.tvd12.ezydata.jpa.EzyJpaDatabaseContextBuilder;
import com.tvd12.ezydata.jpa.loader.EzyJpaDataSourceLoader;
import com.tvd12.ezydata.jpa.loader.EzyJpaEntityManagerFactoryLoader;
import com.tvd12.freechat.test.AbstractMessagePerformanceTest;
import com.tvd12.freechat.test.entity.ChatMessage;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;

public class MySQLMessagePerformanceTest extends AbstractMessagePerformanceTest {

    public static void main(String[] args) {
        MySQLMessageRepository repo = getMessageRepository();
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

    private static MySQLMessageRepository getMessageRepository() {
        EzyDatabaseContext context = databaseContext();
        return context.getRepository(MySQLMessageRepository.class);
    }

    private static EzyDatabaseContext databaseContext() {
        return new EzyJpaDatabaseContextBuilder()
            .properties(properties)
            .entityManagerFactory(entityManagerFactory())
            .scan("com.tvd12.freechat.test.mysql")
            .build();
    }

    private static EntityManagerFactory entityManagerFactory() {
        return new EzyJpaEntityManagerFactoryLoader()
            .entityPackage("com.tvd12.freechat.test.entity")
            .dataSource(dataSource())
            .properties(properties)
            .load("Test");
    }

    private static DataSource dataSource() {
        return new EzyJpaDataSourceLoader()
            .properties(properties, "datasource")
            .load();
    }
}
