package by.fpmibsu.pizza_site.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoInterface {
    default void close(Connection connection) throws DaoException {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
