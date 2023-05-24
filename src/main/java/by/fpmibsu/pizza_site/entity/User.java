package by.fpmibsu.pizza_site.entity;

public record User(UserRole userRole, int id) {
    static public final int ID_NOT_DEFINED = -1;
}
