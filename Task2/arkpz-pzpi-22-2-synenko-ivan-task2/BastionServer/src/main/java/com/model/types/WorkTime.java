package com.model.types;

import com.model.enums.WeekDay;

import java.time.LocalTime;
import java.util.List;

public class WorkTime {
    private List<WeekDay> workDays;
    private LocalTime shiftStart;
    private LocalTime shiftFinish;

    public WorkTime() {
    }

    public WorkTime(List<WeekDay> workDays, LocalTime shiftStart, LocalTime shiftFinish) {
        this.workDays = workDays;
        this.shiftStart = shiftStart;
        this.shiftFinish = shiftFinish;
    }

    public List<WeekDay> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(List<WeekDay> workDays) {
        this.workDays = workDays;
    }

    public LocalTime getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(LocalTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public LocalTime getShiftFinish() {
        return shiftFinish;
    }

    public void setShiftFinish(LocalTime shiftFinish) {
        this.shiftFinish = shiftFinish;
    }

    @Override
    public String toString() {
        return "WorkTime [" +
                "\n  workDays=" + workDays.toString() + "," +
                "\n  shiftStart=" + shiftStart + "," +
                "\n shiftFinish=" + shiftFinish +
                "\n]";
    }
}
