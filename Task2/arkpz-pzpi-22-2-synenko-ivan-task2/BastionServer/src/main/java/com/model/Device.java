package com.model;

import com.model.enums.DeviceType;
import com.model.types.Coordinates;

import java.util.Objects;

public class Device {
    private int deviceId;
    private String name;
    private DeviceType type;
    private Coordinates location;
    private boolean online;

    public Device() {
    }

    public Device(String name, DeviceType type, Coordinates location) {
        this.deviceId = name.hashCode();
        this.name = name;
        this.type = type;
        this.location = location;
        this.online = true;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public Coordinates getLocation() {
        return location;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(name, device.name)
                && type == device.type
                && Objects.equals(location, device.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, location);
    }

}
