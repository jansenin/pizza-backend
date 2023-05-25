package by.fpmibsu.pizza_site.entity;

public enum OrderStatus {
    IN_PROCESS("IN_PROCESS"),
    REJECTED("REJECTED"),
    COMPLETED("COMPLETED");
    private final String name;
    OrderStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
