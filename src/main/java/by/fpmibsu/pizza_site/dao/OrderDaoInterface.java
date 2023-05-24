package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Order;
import by.fpmibsu.pizza_site.entity.User;

import java.util.List;

public interface OrderDaoInterface extends DaoInterface {
    List<Order> findAll() throws DaoException;
    List<Order> findAllUserOrders(User user) throws DaoException;
    Order findOrderById(int id) throws DaoException;
    Order update(Order order) throws DaoException;
    boolean deleteById(int id) throws DaoException;
    boolean insert(Order order) throws DaoException;
}
