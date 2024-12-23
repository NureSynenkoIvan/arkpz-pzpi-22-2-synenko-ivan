package com.config;

//Configuration of
public class RadioConfig extends Config {
    private static RadioConfig instance;

    public final String startAlarmFile = "start.wav";
    public final String stopAlarmFile = "stop.wav";

    public final String URL = "http://192.168.1.74:5000/radio";


    private RadioConfig() {
        name = "radio_config";
    }

    public static RadioConfig getInstance() {
        if (instance == null) {
            instance = new RadioConfig();
        }
        return instance;
    }
}
