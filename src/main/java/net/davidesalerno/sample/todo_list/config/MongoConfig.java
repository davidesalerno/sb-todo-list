package net.davidesalerno.sample.todo_list.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import static java.util.Collections.singletonList;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.host:localhost}")
    private String host;
    @Value("${spring.data.mongodb.port:27017}")
    private int port;
    @Value("${spring.data.mongodb.username:todo_user}")
    private String userName;
    @Value("${spring.data.mongodb.password:passwd}")
    private String secret;
    @Value("${spring.data.mongodb.authentication-database:todo_db}")
    private String authDbName;
    @Value("${spring.data.mongodb.database:todo_db}")
    private String dbName;

    @Override
    public String getDatabaseName() {
        return dbName;
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {

        builder
                .credential(MongoCredential.createCredential(userName, authDbName, secret.toCharArray()))
                .applyToClusterSettings(settings  -> {
                    settings.hosts(singletonList(new ServerAddress(host, port)));
                });
    }
}