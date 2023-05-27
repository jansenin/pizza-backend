package by.fpmibsu.pizza_site.dao;

import java.sql.Connection;

public abstract class BaseDao {
    protected Connection connection;

    public BaseDao(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
