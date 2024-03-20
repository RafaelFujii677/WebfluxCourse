package br.com.webfluxcourse.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@Configuration
public class MongoClientConfig {

    protected String getDatabaseName() {
        return "WebfluxCourse";
    }

    @Bean
    MongoClient mongoClient() {
    	return MongoClients.create("mongodb://user1:password1@localhost:27017");
    }

    @Bean
    ReactiveMongoTemplate reactiveMongoTemplate() {
    	return new ReactiveMongoTemplate(mongoClient(), getDatabaseName());
    }

}
