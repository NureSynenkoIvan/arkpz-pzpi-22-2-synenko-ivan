package com.config;

import com.model.Device;
import com.service.database.DatabaseService;

import java.util.ArrayList;
import java.util.List;

//Configuration of active devices.
//Later may be used to store various connection configurations.
public class DevicesConfig extends Config {
    private static DevicesConfig instance;
    private static List<Device> activeDevices;

    private DevicesConfig() {
        name = "devices_config";
        activeDevices = new ArrayList<Device>();
        activeDevices = DatabaseService
                .getInstance()
                .getDeviceDao()
                .getAll()
                .stream()
                .filter(Device::isOnline)
                .toList();

    }

    public static DevicesConfig getInstance() {
        if (instance == null) {
            instance = new DevicesConfig();
        }
        return instance;
    }

    public List<Device> getActiveDevices() {
        return activeDevices;
    }

    public static void reload() {
        instance = new DevicesConfig();
    }
}
