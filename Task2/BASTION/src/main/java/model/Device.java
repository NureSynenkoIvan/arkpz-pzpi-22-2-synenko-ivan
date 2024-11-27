package model;

import model.enums.DeviceType;
import model.types.Coordinates;

public class Device {
    private int deviceId;
    private String name;
    private DeviceType type;
    private Coordinates location;

    public Device() {
    }

    public Device(int deviceId, String name, DeviceType type, Coordinates location) {
        this.deviceId = deviceId;
        this.name = name;
        this.type = type;
        this.location = location;
    }
}
