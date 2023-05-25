package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.entity.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements UserDaoInterface {
    private final Connection connection;
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users;";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String SQL_UPDATE_USER = "UPDATE users SET role = (CAST (? AS userrole)), password = ?, login = ?  WHERE user_id = ?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = ?";
    private static final String SQL_DELETE_USER_BY_LOGIN = "DELETE FROM users WHERE login = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO users(role, password, login) VALUES (CAST(? AS userrole), ?, ?)";

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                UserRole role = UserRole.valueOf(resultSet.getString("role"));
                String login = resultSet.getString("login");
                users.add(new User(role, login, id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User findUserById(int id) {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("user_id");
                UserRole role = UserRole.valueOf(resultSet.getString("role"));
                String login = resultSet.getString("login");
                user = new User(role, login, id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User findUserByLogin(String login) {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                UserRole role = UserRole.valueOf(resultSet.getString("role"));
                login = resultSet.getString("login");
                user = new User(role, login, id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User updateUser(User user, String password) {
        User idCheckUser = findUserById(user.getId());
        User loginCheckUser = findUserByLogin(user.getLogin());
        if (idCheckUser == null || (loginCheckUser != null && loginCheckUser.getId() != user.getId())) {
            user.setId(User.ID_NOT_DEFINED);
            return user;
        }
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER);

            statement.setString(1, user.getRole().toString());
            statement.setString(2, password);
            statement.setString(3, user.getLogin());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public boolean checkUserPassword(User user, String password) {
        if (password == null) {
            return false;
        }
        String truePassword = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                truePassword = resultSet.getString("password");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return password.equals(truePassword);
    }

    @Override
    public boolean deleteById(int id) {
        int updateRowsCount;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
            statement.setInt(1, id);
            updateRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean deleteByLogin(String login) {
        int updateRowsCount;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_LOGIN);
            statement.setString(1, login);
            updateRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean insert(User user, String password) {
        if (findUserByLogin(user.getLogin()) != null) {
            user.setId(User.ID_NOT_DEFINED);
            return false;
        }
        int updateRowsCount;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER);
            statement.setString(1, user.getRole().toString());
            statement.setString(2, password);
            statement.setString(3, user.getLogin());
            updateRowsCount = statement.executeUpdate();
            if (updateRowsCount > 0) {
                user.setId(findUserByLogin(user.getLogin()).getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updateRowsCount > 0;
    }
}
