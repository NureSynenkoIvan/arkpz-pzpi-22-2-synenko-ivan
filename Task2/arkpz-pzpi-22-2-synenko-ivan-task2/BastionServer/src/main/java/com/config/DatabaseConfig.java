package com.config;

import com.model.*;

import java.util.Map;

public class DatabaseConfig extends Config {
    private static DatabaseConfig instance;
    public String URI = "mongodb://localhost:27017";
    public String DATABASE_NAME = "bastion";
    public Map<Class, String> COLLECTIONS = Map.of(
            Employee.class, "employee",
            Device.class, "device",
            SkyObject.class, "sky_object",
            SkyState.class, "sky_state",
            MessageScenario.class, "message_scenario",
            Config.class, "config"
    );

    public DatabaseConfig() {
        name = "database_config";
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }
}
