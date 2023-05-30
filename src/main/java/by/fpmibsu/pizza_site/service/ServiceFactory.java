package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.exception.TransactionException;

public interface ServiceFactory {
    <Type extends Service> Type getService(Class<Type> key) throws TransactionException;
    void close() throws TransactionException;
}
