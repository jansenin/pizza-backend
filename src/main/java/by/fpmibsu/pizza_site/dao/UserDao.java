package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.database.ConnectionCreator;
import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.entity.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements UserDaoInterface {
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users;";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String SQL_UPDATE_USER = "UPDATE users SET user_id = ?, role = ?, password = ?, login = ?,  WHERE user_id = ?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO users VALUES (?, ?, ?, ?)";


    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionCreator.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                UserRole role = resultSet.getObject("role", UserRole.class);
                users.add(new User(role, id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return users;
    }

    @Override
    public User findUserById(int id) throws DaoException {
        Connection connection = null;
        UserRole role = null;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("user_id");
                role = resultSet.getObject("role", UserRole.class);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return new User(role, id);
    }

    @Override
    public User findUserByLogin(String login) throws DaoException {
        Connection connection = null;
        int id = 0;
        UserRole role = null;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("user_id");
                role = resultSet.getObject("role", UserRole.class);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return new User(role, id);
    }

    @Override
    public boolean checkUserPassword(User user, String password) throws DaoException {
        if (password == null) {
            return false;
        }
        Connection connection = null;
        String truePassword = null;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setInt(1, user.id());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                truePassword = resultSet.getString("password");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return password.equals(truePassword);
    }

    @Override
    public boolean deleteByUser(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(int id) throws DaoException {
        return false;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        return false;
    }
}
