package com.service.database.dao.impl;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import com.service.database.dao.AbstractDao;
import com.service.database.dao.MessageScenarioDao;
import com.model.MessageScenario;
import org.bson.conversions.Bson;
import com.service.database.utils.MongoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageScenarioDaoImpl extends AbstractDao implements MessageScenarioDao {
    public MessageScenarioDaoImpl(MongoUtil mongoUtil) {
        super(mongoUtil, MessageScenario.class);
        getCollection().createIndex(Indexes.ascending("scenarioName"), new IndexOptions().unique(true));
    }

    @Override
    public void save(MessageScenario scenario) {
        MongoCollection<MessageScenario> scenarioCollection = getCollection();
        try {
            scenarioCollection.insertOne(scenario);
        } catch (
                MongoWriteException e) {
            if (e.getError().getCategory() == ErrorCategory.DUPLICATE_KEY) {
                throw new IllegalArgumentException("A message scenario with this name already exists!");
            }
            throw e;
        }
    }

    @Override
    public void update(MessageScenario scenario) {
        MongoCollection<MessageScenario> scenarioCollection = getCollection();
        Bson findQuery = Filters.eq("scenarioName", scenario.scenarioName);

        scenarioCollection.findOneAndReplace(findQuery, scenario);
    }

    @Override
    public void edit(MessageScenario scenario, Map<String, String> changes) {
        MongoCollection<MessageScenario> deviceCollection = getCollection();
        Bson findQuery = Filters.eq("scenarioName", scenario.scenarioName);

        List<Bson> updates = new ArrayList<>();
        for (Map.Entry<String, String> change : changes.entrySet()) {
            updates.add(Updates.set(change.getKey(), change.getValue()));
        }
        Bson updateQuery = Updates.combine(updates);

        deviceCollection.findOneAndUpdate(findQuery, updateQuery);
    }


    @Override
    public List<MessageScenario> getAll() {
        MongoCollection<MessageScenario> scenarioCollection = getCollection();
        return scenarioCollection
                .find()
                .into(new ArrayList<>());
    }

    @Override
    public void delete(MessageScenario scenario) {
        MongoCollection<MessageScenario> scenarioCollection = getCollection();
        scenarioCollection.deleteOne(
                Filters.eq("scenarioName", scenario.scenarioName));
    }

    @Override
    public MessageScenario get(String scenarioName) {
        MongoCollection<MessageScenario> scenarioCollection = getCollection();
        Bson findQuery = Filters.eq("scenarioName", scenarioName);

        return scenarioCollection.find(findQuery).first();
    }
}
