package com.service.database.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.service.database.utils.MongoUtil;


//Class, which ensures that all dao classes are accessing MongoDB in the same way
public abstract class AbstractDao<T> {
    private final MongoUtil mongoUtil;
    private final Class<T> entityClass;
    private final String collectionName;

    protected AbstractDao(MongoUtil mongoUtil, Class<T> entityClass) {
        this.mongoUtil = mongoUtil;
        this.entityClass = entityClass;
        //All classes permitted for storage in DB and their corresponding collections
        //are stored in a table inside DatabaseConfig in MongoUtil.
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
