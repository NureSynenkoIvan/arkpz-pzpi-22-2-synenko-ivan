package org.example.model.server;

import java.util.Objects;

public class Coordinates {
    public double latitude;
    public double longitude;

    public Coordinates() {

    }

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Coordinates [" +
                "\n  latitude=" + latitude + "," +
                "\n  longitude=" + longitude + "\n]";
    }

    public String toJson() {
        return "{\"latitude\":" + latitude + ",\"longitude\":" + longitude + "}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Coordinates that)) return false;
        return Double.compare(latitude, that.latitude) == 0 && Double.compare(longitude, that.longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }


}
