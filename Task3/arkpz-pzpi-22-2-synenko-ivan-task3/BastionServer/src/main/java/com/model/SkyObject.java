package com.model;

import com.model.types.Coordinates;

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

    public SkyObject(int deviceID,
                     Coordinates location,
                     double height,
                     double direction,
                     double heightVector,
                     double speed,
                     boolean hasTransponder) {
        this.deviceID = deviceID;
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
                "deviceID=" + deviceID + "\n" +
                ", location=" + location + "\n" +
                ", height=" + height + "\n" +
                ", direction=" + direction + "\n" +
                ", heightVector=" + heightVector + "\n" +
                ", speed=" + speed + "\n" +
                ", hasTransponder=" + hasTransponder + "\n" +
                '}';
    }

    public void shiftToCoordinateSystem(Coordinates zeroPoint) {

    }
}
