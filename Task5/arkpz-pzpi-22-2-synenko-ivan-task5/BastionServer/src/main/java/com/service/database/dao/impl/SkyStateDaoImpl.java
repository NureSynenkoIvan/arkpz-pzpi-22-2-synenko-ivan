package com.service.database.dao.impl;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import com.service.database.dao.AbstractDao;
import com.service.database.dao.SkyStateDao;
import com.model.SkyState;
import com.model.enums.SortingOrder;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.service.database.utils.MongoUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SkyStateDaoImpl extends AbstractDao implements SkyStateDao {

    public SkyStateDaoImpl(MongoUtil mongoUtil) {
        super(mongoUtil, SkyState.class);
        getCollection().createIndex(Indexes.ascending("time"), new IndexOptions().unique(true));
    }

    @Override
    public void save(SkyState skyState) {
        MongoCollection<SkyState> skyStateCollection = getCollection();
        try {
            skyStateCollection.insertOne(skyState);
        } catch (MongoWriteException e) {
            if (e.getError().getCategory() == ErrorCategory.DUPLICATE_KEY) {
                throw new IllegalArgumentException("An employee with this phone number already exists!");
            }
            throw e;
        }
    }

    @Override
    public void saveAll(Collection<SkyState> list) {
        MongoCollection<SkyState> skyStateCollection = getCollection();
        try {
            skyStateCollection.insertMany(new ArrayList<>(list));
        } catch (MongoWriteException e) {
            if (e.getError().getCategory() == ErrorCategory.DUPLICATE_KEY) {
                throw new IllegalArgumentException("An employee with this phone number already exists!");
            }
            throw e;
        }
    }

    @Override
    public List<SkyState> getAll(SortingOrder order) {
        Bson sortQuery;
        if (order == SortingOrder.ASC) {
            sortQuery = Sorts.ascending("time");
        } else {
            sortQuery = Sorts.descending("time");
        }

        MongoCollection<SkyState> skyStateCollection = getCollection();
        return skyStateCollection
                .find()
                .sort(sortQuery)
                .into(new ArrayList<>());
    }

    @Override
    public void delete(SkyState skyState) {
        MongoCollection<SkyState> skyStateCollection = getCollection();
        skyStateCollection.deleteOne(
                Filters.eq("time", skyState.time));
    }

    @Override
    public void deleteAll() {
        MongoCollection<SkyState> skyStateCollection = getCollection();
        skyStateCollection.deleteMany(new Document());
    }
}
