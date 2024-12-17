package com.service.database.dao;

import com.model.Device;
import com.model.enums.SortingOrder;

import java.util.List;
import java.util.Map;

public interface DeviceDao {
    public void save(Device device);

    public void update(Device device);

    public void edit(Device device, Map<String, String> changes);

    public List<Device> getAll();

    public List<Device> getAll(SortingOrder order);

    public void delete(Device device);

    Device get(String deviceName);
}
