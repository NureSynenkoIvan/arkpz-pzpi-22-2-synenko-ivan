package model.types;

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
}
