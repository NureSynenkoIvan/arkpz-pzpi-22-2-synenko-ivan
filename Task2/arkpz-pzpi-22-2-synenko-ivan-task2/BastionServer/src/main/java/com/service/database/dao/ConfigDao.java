package com.service.database.dao;

import com.config.Config;
import com.model.Device;
import com.model.enums.SortingOrder;

import java.util.List;
import java.util.Map;

public interface ConfigDao {
    public void save(Config config);

    public void update(Config config);

    Config get(String configName);

    public List<Device> getAll();

    public void delete(Config config);

}
