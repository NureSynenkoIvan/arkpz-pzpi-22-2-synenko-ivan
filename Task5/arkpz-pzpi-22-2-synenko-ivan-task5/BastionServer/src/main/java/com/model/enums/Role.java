package com.model.enums;

public enum Role {
    ADMINISTRATOR,
    DISPATCHER,
    USER;

    @Override
    public String toString() {
        return switch (this) {
            case ADMINISTRATOR -> "Administrator";
            case DISPATCHER -> "Dispatcher";
            case USER -> "User";
            default -> super.toString();
        };
    }
}
