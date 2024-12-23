package org.example.model.server;

import java.util.Objects;

public class SkyObject {
    public int deviceID;
    public Coordinates location;
    public double height;
    public double direction;
    public double heightVector;
    public double speed;
    public boolean hasTransponder;

    public SkyObject() {

    }

    public SkyObject(
                     Coordinates location,
                     double height,
                     double direction,
                     double heightVector,
                     double speed,
                     boolean hasTransponder) {
        this.deviceID = -1854719477;
        this.location = location;
        this.height = height;
        this.heightVector = heightVector;
        this.direction = direction;
        this.speed = speed;
        this.hasTransponder = hasTransponder;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof SkyObject skyObject)) return false;
        return deviceID == skyObject.deviceID
                && Double.compare(height, skyObject.height) == 0
                && Double.compare(direction, skyObject.direction) == 0
                && Double.compare(speed, skyObject.speed) == 0
                && Objects.equals(location, skyObject.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceID, location, height, direction, speed);
    }

    @Override
    public String toString() {
        return "SkyObject{" +
                "deviceID=" + deviceID +
                ", location=" + location +
                ", height=" + height +
                ", direction=" + direction +
                ", heightVector=" + heightVector +
                ", speed=" + speed +
                ", hasTransponder=" + hasTransponder +
                '}';
    }

    public String toJson() {
        return "{\"deviceID\":" + deviceID + ", "
                + "\"location\":" + location.toJson() + ", "
                + "\"height\":" + height + ", "
                + "\"direction\":" + direction + ", "
                + "\"heightVector\":" + heightVector + ", "
                + "\"speed\":" + speed + ", "
                + "\"hasTransponder\":" + hasTransponder + "}";
    }
}
