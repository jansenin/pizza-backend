package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.exception.TransactionException;

public interface Transaction {
    <Type extends Dao<?>> Type createDao(Class<Type> key) throws TransactionException;

    void commit() throws TransactionException;

    void rollback() throws TransactionException;
}
