package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.exception.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactory implements TransactionFactoryInterface {
    private final Connection connection;

    public TransactionFactory() throws TransactionException {
        try {
            connection = ConnectionCreator.createConnection();
        } catch(SQLException e) {
            throw new TransactionException(e);
        }
    }

    @Override
    public TransactionImpl createTransaction() {
        return new TransactionImpl(connection);
    }

    @Override
    public void close() throws TransactionException {
        try {
            connection.close();
        } catch(SQLException e) {
            throw new TransactionException(e);
        }
    }
}
