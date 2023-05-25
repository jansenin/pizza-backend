package by.fpmibsu.pizza_site.entity;

public class User {
    private UserRole role;
    private int id;
    static public final int ID_NOT_DEFINED = -1;
    public User(UserRole role, int id) {
        this.role = role;
        this.id = id;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User [ id = " + id + " role = " + role.toString() + " ]";
    }
}
