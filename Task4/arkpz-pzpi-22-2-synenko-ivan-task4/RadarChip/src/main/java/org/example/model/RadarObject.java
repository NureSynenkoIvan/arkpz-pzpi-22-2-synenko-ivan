package org.example.model;

import org.example.model.server.Coordinates;
import org.example.model.server.SkyObject;

public class RadarObject extends RadarInformation {
    public float radius;
    public float azimuth;
    public float zenith;
    public float directionX;
    public float directionZ;
    public float speed;
    public boolean hasTransponder;

    public RadarObject() {}

    public RadarObject(
            float radius,
            float azimuth,
            float zenith,
            float directionX,
            float directionZ,
            float speed,
            boolean hasTransponder) {
        this.radius = radius;
        this.azimuth = azimuth;
        this.zenith = zenith;
        this.directionX = directionX;
        this.directionZ = directionZ;
        this.speed = speed;
        this.hasTransponder = hasTransponder;
    }

    public SkyObject toCartesianCoordinates() {
        double x = radius * Math.sin(zenith) * Math.cos(azimuth);
        double y = radius * Math.sin(zenith) * Math.sin(azimuth);
        double z = radius * Math.cos(zenith);

        return new SkyObject(
                new Coordinates(x, y),
                z,
                directionX,
                directionZ,
                speed,
                hasTransponder
        );
    }
}
