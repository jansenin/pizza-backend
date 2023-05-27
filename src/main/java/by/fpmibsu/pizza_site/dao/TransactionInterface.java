package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.exception.TransactionException;

public interface TransactionInterface {
    <Type extends DaoInterface<?>> Type createDao(Class<Type> key) throws TransactionException;

    void commit() throws TransactionException;

    void rollback() throws TransactionException;
}
