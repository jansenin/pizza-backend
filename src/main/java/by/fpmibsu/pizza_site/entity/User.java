package by.fpmibsu.pizza_site.entity;
public class User extends Entity {
    private UserRole role;
    private String login;
    private String password;
    static public final int ID_NOT_DEFINED = -1;
    public User(UserRole role, String login, int id, String password) {
        this.password = password;
        this.role = role;
        this.id = id;
        this.login = login;
    }

    public void setRole(UserRole role) {
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        User toCompare = (User) object;
        return login.equals(toCompare.login)
                && role.equals(toCompare.role)
                && password.equals(toCompare.password);
    }

    @Override
    public String toString() {
        return "User [ id = " + id + " login = " + login + " role = " + role.toString() + " password = " + password + " ]";
    }
}
