package com.model.enums;

public enum DeviceType {
    RADAR,
    RADIO_STATION;

    @Override
    public String toString() {
        return switch (this) {
            case RADAR -> "Radar";
            case RADIO_STATION -> "Radio station";
            default -> super.toString();
        };
    }
}
