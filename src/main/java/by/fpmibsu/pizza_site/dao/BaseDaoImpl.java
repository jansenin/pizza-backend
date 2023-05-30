package by.fpmibsu.pizza_site.dao;

import java.sql.Connection;

public abstract class BaseDaoImpl {
    protected Connection connection;
    public BaseDaoImpl(Connection connection) {
        this.connection = connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
