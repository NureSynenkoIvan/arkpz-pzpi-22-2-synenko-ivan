package com.config;

import com.model.*;
import com.model.types.Coordinates;

import java.util.Map;

public class LocationConfig extends Config {

    private static LocationConfig instance;
    private Coordinates coordinates;

    public LocationConfig() {
        coordinates = new Coordinates(2, 2); //Sample coordinates
    }

    public static LocationConfig getInstance() {
        if (instance == null) {
            instance = new LocationConfig();
        }
        return instance;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
