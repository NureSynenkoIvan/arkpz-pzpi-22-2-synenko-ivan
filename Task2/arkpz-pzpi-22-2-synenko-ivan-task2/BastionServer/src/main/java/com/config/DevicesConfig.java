package com.config;

import com.model.Device;
import com.service.database.DatabaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DevicesConfig extends Config {
    private static DevicesConfig instance;
    private static List<Device> activeDevices;

    private DevicesConfig() {
        activeDevices = new ArrayList<Device>();
        activeDevices = DatabaseService
                .getInstance()
                .getDeviceDao()
                .getAll()
                .stream()
                .filter(device -> device.isOnline())
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
