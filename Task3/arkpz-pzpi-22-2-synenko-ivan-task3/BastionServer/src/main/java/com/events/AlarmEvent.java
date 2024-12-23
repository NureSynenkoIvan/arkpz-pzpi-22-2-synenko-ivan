package com.events;

import com.model.SkyObject;

import java.util.Date;
import java.util.List;

//Used to store information about alert. May be changed to store more data.
public class AlarmEvent {
    public Date date;
    public List<SkyObject> alarmingObjects;

    public AlarmEvent(List<SkyObject> alarmingObjects) {
        date = new Date();
        this.alarmingObjects = alarmingObjects;
    }

    @Override
    public String toString() {
        return "ALARM! \n time: " + date.toString() + alarmingObjects.toString();
    }
}
