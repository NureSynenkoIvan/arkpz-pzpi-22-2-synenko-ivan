package model;

import model.types.Coordinates;

public class SkyObject {
    public Coordinates location;
    public double height;
    public double direction;
    public double speed;

    public SkyObject() {

    }

    public SkyObject(Coordinates location, double height, double direction, double speed) {
        this.location = location;
        this.height = height;
        this.direction = direction;
        this.speed = speed;
    }
}
