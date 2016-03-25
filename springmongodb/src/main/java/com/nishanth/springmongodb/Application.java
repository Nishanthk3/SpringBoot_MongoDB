package com.nishanth.springmongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@SpringBootApplication
public class Application {

	Properties prop = new Properties();
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}	
}
	
@Configuration
@PropertySource("classpath:database.properties")
class MongoConfiguration {

	@Value("${mongo.host}") private String mongoHost;
	@Value("${mongo.port}") private String mongoPort; 
	@Value("${mongo.database}") private String mongoDatabse;
	@Value("${mongo.username}") private String mongoUserName; 
	@Value("${mongo.password}") private String mongoPassword;

	@Bean
	public MongoDbFactory getMongoDbFactory() {
		List<ServerAddress> serverList = new ArrayList<ServerAddress>();
		try {
			serverList.add(new ServerAddress(mongoHost, Integer.parseInt(mongoPort)));
			List<MongoCredential> credsList = new ArrayList<MongoCredential>();
			credsList.add(MongoCredential.createMongoCRCredential(mongoUserName, mongoDatabse, mongoPassword.toCharArray()));
			MongoClient mongoclient = new MongoClient(serverList, credsList);
			return new SimpleMongoDbFactory(mongoclient, mongoDatabse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public @Bean
	MongoTemplate mongoTemplate() throws Exception {
		// this MappingMongoConverter is used to avoid default fields like _class field 
		MappingMongoConverter converter = new MappingMongoConverter(getMongoDbFactory(), new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		MongoTemplate mongoTemplate = new MongoTemplate(getMongoDbFactory(), converter);
		return mongoTemplate;
	}
}
