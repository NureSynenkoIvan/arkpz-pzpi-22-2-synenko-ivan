package model;

import model.enums.Role;
import model.types.Coordinates;
import org.bson.Document;

public class Employee {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Role role;
    private String position;
    private Coordinates location;
    private long passwordHash;

    public Employee() {

    }

    public Employee(String firstName,
                String lastName,
                String phoneNumber,
                Role role,
                String position,
                Coordinates location,
                long passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.position = position;
        this.location = location;
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Coordinates getLocation() {
        return location;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }

    public long getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(long passwordHash) {
        this.passwordHash = passwordHash;
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
                ",\n  passwordHash=" + passwordHash + "\n]";
    }

    public org.bson.Document toDocument() {
        return new Document();
    }

}
