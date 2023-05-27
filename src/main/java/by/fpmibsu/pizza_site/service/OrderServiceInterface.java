package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.entity.Order;
import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public interface OrderServiceInterface extends ServiceInterface {
    List<Order> findAll() throws DaoException, TransactionException;
    Order findById(int id) throws DaoException, TransactionException;
    Order update(Order order) throws DaoException, TransactionException;
    boolean deleteById(int id) throws DaoException, TransactionException;
    boolean insert(Order order) throws DaoException, TransactionException;
    List<Order> findAllUserOrders(User user) throws DaoException, TransactionException;
}
