package com.service.database.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.utils.MongoUtil;

public abstract class AbstractDao<T> {
    private final MongoUtil mongoUtil;
    private final Class<T> entityClass;
    private final String collectionName;

    protected AbstractDao(MongoUtil mongoUtil, Class<T> entityClass) {
        this.mongoUtil = mongoUtil;
        this.entityClass = entityClass;
        this.collectionName = mongoUtil.getCollectionNameForClass(entityClass);
    }

    protected MongoCollection<T> getCollection() {
        MongoDatabase database = mongoUtil.getDatabase();
        if (database == null) {
            throw new IllegalStateException("Database connection is not initialized.");
        }
        return database.getCollection(collectionName, entityClass);
    }

}
