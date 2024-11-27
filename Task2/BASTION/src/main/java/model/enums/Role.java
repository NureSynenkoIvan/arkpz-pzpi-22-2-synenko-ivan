package model.enums;

public enum Role {
    ADMINISTRATOR,
    DISPATCHER,
    USER;

    @Override
    public String toString() {
        switch (this) {
            case ADMINISTRATOR:
                return "Administrator";
            case DISPATCHER:
                return "Dispatcher";
            case USER:
                return "User";
            default:
                return super.toString();
        }
    }
}
