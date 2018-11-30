package org.jnd.microservices.mongodb;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoRepositories(basePackages = "org.jnd.microservices.mongodb")
@PropertySource("classpath:mongo.properties")
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${my.property:not_found}")
    private String host = null;
    @Value("${my.property:not_found}")
    private String port = null;
    @Value("${my.property:not_found}")
    private String username = null;
    @Value("${my.property:not_found}")
    private String password = null;
    @Value("${my.property:not_found}")
    private String dbname = null;

    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return env.getProperty(dbname);
    }

    @Override
    public MongoClient mongoClient() {
        MongoCredential creds = MongoCredential.createCredential(username, dbname, password.toCharArray());

        ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(port));
        List serverlist = new ArrayList();
        serverlist.add(serverAddress);

        return new MongoClient(serverlist, creds, new MongoClientOptions.Builder().build());
    }

    @Override
    protected String getMappingBasePackage() {
        return "org.jnd.microservices.mongodb";
    }

}
