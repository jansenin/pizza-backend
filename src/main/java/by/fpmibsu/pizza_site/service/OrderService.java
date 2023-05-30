package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.entity.Order;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public interface OrderService extends Service {
    List<Order> findAll() throws DaoException, TransactionException;
    Order findById(Integer id) throws DaoException, TransactionException;
    Order update(Order order) throws DaoException, TransactionException;
    void deleteById(Integer id) throws DaoException, TransactionException;
    void insert(Order order) throws DaoException, TransactionException;
    List<Order> findAllUserOrders(Integer userId) throws DaoException, TransactionException;
}
