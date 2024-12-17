package com.utils;

import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.ClientSession;
import com.config.DatabaseConfig;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.Map;

public class MongoUtil {
    private static DatabaseConfig databaseConfig;
    private static String URI;
    private static String DATABASE_NAME;
    private static Map<Class, String> COLLECTIONS;

    private MongoClient mongoClient;
    private MongoDatabase database;


    public MongoUtil(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
        URI = databaseConfig.URI;
        DATABASE_NAME = databaseConfig.DATABASE_NAME;
        COLLECTIONS = databaseConfig.COLLECTIONS;
        mongoClient = MongoClients.create(URI);
    }

    public void connect() {
        try {
            database = mongoClient
                    .getDatabase(DATABASE_NAME)
                    .withCodecRegistry(getCodecRegistry());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public ClientSession getClientSession() {
        return mongoClient.startSession();
    }

    public void disconnect() {
        database = null;
    }

    public String getCollectionNameForClass(Class<?> clazz) {
        return COLLECTIONS.getOrDefault(clazz, clazz.getSimpleName().toLowerCase());
    }

    private CodecRegistry getCodecRegistry() {
        return fromRegistries(
                MongoClientSettings
                        .getDefaultCodecRegistry(),
                CodecRegistries
                        .fromProviders(
                        PojoCodecProvider
                                .builder()
                                .automatic(true)
                                .build()));

    }
}