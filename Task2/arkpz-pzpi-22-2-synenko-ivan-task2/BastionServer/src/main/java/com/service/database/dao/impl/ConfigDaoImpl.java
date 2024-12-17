package com.service.database.dao.impl;

import com.config.Config;
import com.model.Device;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.service.database.dao.AbstractDao;
import com.service.database.dao.ConfigDao;
import com.utils.MongoUtil;

import java.util.List;

public class ConfigDaoImpl extends AbstractDao implements ConfigDao {
    public ConfigDaoImpl(MongoUtil mongoUtil) {
        super(mongoUtil, Config.class);
    }

    @Override
    public void save(Config config) {

    }

    @Override
    public void update(Config config) {

    }

    @Override
    public Config get(String configName) {
        return null;
    }

    @Override
    public List<Device> getAll() {
        return List.of();
    }

    @Override
    public void delete(Config config) {

    }
}
