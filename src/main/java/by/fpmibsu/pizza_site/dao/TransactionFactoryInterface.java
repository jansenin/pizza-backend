package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.exception.TransactionException;

public interface TransactionFactoryInterface {
    TransactionImpl createTransaction();
    void close() throws TransactionException;
}
