package com.model;


import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class SkyState implements Cloneable {
    public Date time;
    public boolean isAlert;
    public Collection<SkyObject> skyObjects;


    public SkyState() {

    }

    public SkyState(Date time,
                    boolean isAlert,
                    Collection<SkyObject> skyObjects) {
        this.time = time;
        this.isAlert = isAlert;
        this.skyObjects = skyObjects;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof SkyState skyState)) return false;
        return isAlert == skyState.isAlert
                && Objects.equals(time, skyState.time)
                && Objects.equals(skyObjects, skyState.skyObjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, isAlert, skyObjects);
    }

    @Override
    public String toString() {
        return "time=" + time + ",\n isAlert=" + isAlert + "\n, skyObjects=" + skyObjects.toString();
    }

    @Override
    public SkyState clone() {
        SkyState skyState = new SkyState();
        skyState.time = time;
        skyState.isAlert = isAlert;
        skyState.skyObjects = skyObjects;
        return skyState;
    }
}
