package ptithcm.device_maintenance.request;

public enum RequestStatus {
    PENDING("PENDING"),
    IN_PROCESS("IN PROCESS"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED");

    private final String displayValue;

    RequestStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    @Override
    public String toString() {
        return displayValue;
    }
}
