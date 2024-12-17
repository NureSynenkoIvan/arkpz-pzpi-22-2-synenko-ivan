package com.model;

import com.model.enums.Role;
import com.model.types.Coordinates;
import com.model.types.WorkTime;

import java.util.Objects;

public class Employee {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Role role;
    private String position;
    private Coordinates location;
    private WorkTime workTime;
    private boolean onDuty;
    private long passwordHash;

    public Employee() {

    }

    public Employee(String firstName,
                String lastName,
                String phoneNumber,
                Role role,
                String position,
                Coordinates location,
                WorkTime workTime,
                boolean onDuty,
                long passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.position = position;
        this.location = location;
        this.workTime = workTime;
        this.onDuty = onDuty;
        this.passwordHash = passwordHash;
    }

    public long getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(long passwordHash) {
        this.passwordHash = passwordHash;
    }

    public WorkTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }

    public Coordinates getLocation() {
        return location;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isOnDuty() {
        return onDuty;
    }

    public void setOnDuty(boolean onDuty) {
        this.onDuty = onDuty;
    }

    @Override
    public String toString() {
        return "Employee " +
                "[\n  firstName=" + firstName + "," +
                "\n  lastName=" + lastName + "," +
                "\n  phoneNumber=" + phoneNumber +
                ",\n  role=" + (role != null ? role.toString() : "null") +
                ",\n  position=" + position +
                ",\n  location=" + (location != null ? location.toString() : "null") +
                ",\n  workTime=" + (workTime != null ? workTime.toString() : "null") +
                "]";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Employee employee)) return false;
        return passwordHash == employee.passwordHash
                && Objects.equals(firstName, employee.firstName)
                && Objects.equals(lastName, employee.lastName)
                && Objects.equals(phoneNumber, employee.phoneNumber)
                && role == employee.role
                && Objects.equals(position, employee.position)
                && Objects.equals(location, employee.location)
                && Objects.equals(workTime, employee.workTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumber,
                role, position, location, workTime, passwordHash);
    }
}
