package com.events;

import com.model.SkyObject;

import java.util.List;

public class AlarmEvent {
    public List<SkyObject> alarmingObjects;

    public AlarmEvent(List<SkyObject> alarmingObjects) {
        this.alarmingObjects = alarmingObjects;
    }
}
