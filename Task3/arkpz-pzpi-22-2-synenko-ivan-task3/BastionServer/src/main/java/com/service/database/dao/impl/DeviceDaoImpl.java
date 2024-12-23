package com.service.database.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import com.service.database.dao.AbstractDao;
import com.service.database.dao.DeviceDao;
import com.model.Device;
import com.model.enums.SortingOrder;
import org.bson.conversions.Bson;
import com.service.database.utils.MongoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeviceDaoImpl extends AbstractDao implements DeviceDao {

    public DeviceDaoImpl(MongoUtil mongoUtil) {
        super(mongoUtil, Device.class);
        getCollection().createIndex(Indexes.ascending("deviceId"), new IndexOptions().unique(true));
    }

    @Override
    public void save(Device device) {
        MongoCollection<Device>  deviceCollection = getCollection();
        deviceCollection.insertOne(device);
    }

    @Override
    public void update(Device device) {
        MongoCollection<Device>  deviceCollection = getCollection();
        Bson findQuery = Filters.eq("name", device.getName());
        deviceCollection.findOneAndReplace(findQuery, device);
    }

    @Override
    public void edit(Device device, Map<String, String> changes) {
        MongoCollection<Device>  deviceCollection = getCollection();
        Bson findQuery = Filters.eq("name", device.getName());

        List<Bson> updates = new ArrayList<>();
        for (Map.Entry<String, String> change : changes.entrySet()) {
            updates.add(Updates.set(change.getKey(), change.getValue()));
        }
        Bson updateQuery = Updates.combine(updates);

        deviceCollection.findOneAndUpdate(findQuery, updateQuery);
    }

    @Override
    public List<Device> getAll() {
        MongoCollection<Device>  deviceCollection = getCollection();
        List<Device> list = new ArrayList<>();
        deviceCollection.find().into(list);
        return list;
    }

    @Override
    public List<Device> getAll(SortingOrder order) {
        Bson sortQuery;
        if (order == SortingOrder.ASC) {
            sortQuery = Sorts.ascending("name");
        } else {
            sortQuery = Sorts.descending("name");
        }

        MongoCollection<Device> deviceCollection = getCollection();
        return deviceCollection
                .find()
                .sort(sortQuery)
                .into(new ArrayList<>());
    }

    @Override
    public void delete(Device device) {
        MongoCollection<Device> deviceCollection = getCollection();
        deviceCollection.deleteOne(
                Filters.eq("name", device.getName()));
    }

    @Override
    public Device get(String name) {
        MongoCollection<Device> deviceCollection = getCollection();
        Bson findQuery = Filters.eq("name", name);
        return deviceCollection.find(findQuery).first();
    }
}
