package roz.power.market.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    @Value("${spring.data.mongo.url}")
    private String url;

    @Value("${spring.data.mongo.name.db}")
    private String db;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(url);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), db);
    }
}
