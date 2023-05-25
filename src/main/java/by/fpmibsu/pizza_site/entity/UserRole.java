package by.fpmibsu.pizza_site.entity;

public enum UserRole {
    CLIENT("CLIENT"),
    STAFF("STAFF"),
    ADMIN("ADMIN");

    private final String name;
    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
