package model.types;

import model.enums.WeekDay;

import java.time.LocalTime;

public class WorkTime {
    public WeekDay[] workDays;
    public LocalTime shiftStart;
    public LocalTime shiftFinish;

    public WorkTime(WeekDay[] workDays, LocalTime shiftStart, LocalTime shiftFinish) {
        this.workDays = workDays;
        this.shiftStart = shiftStart;
        this.shiftFinish = shiftFinish;
    }
}
