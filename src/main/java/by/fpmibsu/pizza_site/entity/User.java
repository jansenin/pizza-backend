package by.fpmibsu.pizza_site.entity;

public class User {
    private UserRole role;
    private String login;
    private int id;
    static public final int ID_NOT_DEFINED = -1;
    public User(UserRole role, String login, int id) {
        this.role = role;
        this.id = id;
        this.login = login;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "User [ id = " + id + " login = " + login + " role = " + role.toString() + " ]";
    }
}
