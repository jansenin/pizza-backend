package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.entity.UserRole;
import by.fpmibsu.pizza_site.exception.DaoException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users;";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String SQL_UPDATE_USER = "UPDATE users SET role = (CAST (? AS userrole)), password = ?, login = ?  WHERE user_id = ?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = ?";
    private static final String SQL_DELETE_USER_BY_LOGIN = "DELETE FROM users WHERE login = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO users(role, password, login) VALUES (CAST(? AS userrole), ?, ?)";

    private static final Logger logger = LogManager.getLogger(IngredientDaoImpl.class);

    public UserDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS)) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("user_id");
                UserRole role = UserRole.valueOf(resultSet.getString("role"));
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                User user = new User(role, login, password);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public User findById(Integer id) throws DaoException {
        User user = null;
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("user_id");
                UserRole role = UserRole.valueOf(resultSet.getString("role"));
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                user = new User(role, login, password);
                user.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored) {}
        }
        return user;
    }

    @Override
    public User findUserByLogin(String login) throws DaoException {
        User user = null;
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                UserRole role = UserRole.valueOf(resultSet.getString("role"));
                login = resultSet.getString("login");
                String password = resultSet.getString("password");
                user = new User(role, login, password);
                user.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored) {}
        }
        return user;
    }

    @Override
    public User update(User user) throws DaoException {
        User idCheckUser = findById(user.getId());
        User loginCheckUser = findUserByLogin(user.getLogin());
        if (idCheckUser == null) {
            logger.info("attempt to update user with no existing id " + user.getId());
            user.setId(null);
            return user;
        }
        if (loginCheckUser != null && !loginCheckUser.getId().equals(user.getId())) {
            logger.info("attempt to set user already existing login " + user.getLogin());
            user.setId(null);
            return user;
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
            statement.setString(1, user.getRole().toString());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getLogin());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public boolean checkUserPassword(User user, String password) throws DaoException {
        if (password == null) {
            return false;
        }
        String truePassword = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                truePassword = resultSet.getString("password");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return password.equals(truePassword);
    }

    @Override
    public void deleteById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteByLogin(String login) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_LOGIN)) {
            statement.setString(1, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void insert(User user) throws DaoException {
        if (findUserByLogin(user.getLogin()) != null) {
            user.setId(null);
            logger.info("attempt to add existing user " + user.getLogin());
            return;
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, user.getRole().toString());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getLogin());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            } else {
                logger.error("There is no auto incremented index after trying to add record into table `users`");
                throw new DaoException();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
