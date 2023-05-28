package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.exception.TransactionException;

public interface ServiceFactoryInterface {
    <Type extends ServiceInterface> Type getService(Class<Type> key) throws TransactionException;
    void close() throws TransactionException;
}
