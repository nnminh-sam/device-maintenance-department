package ptithcm.device_maintenance.request;

public enum RequestType {
    MAINTENANCE("MAINTENANCE"),
    LIQUIDATION("LIQUIDATION"),
    REPAIRMENT("REPAIRMENT");

    private final String displayValue;

    RequestType(String displayValue) {
        this.displayValue = displayValue;
    }

    @Override
    public String toString() {
        return this.displayValue;
    }
}
