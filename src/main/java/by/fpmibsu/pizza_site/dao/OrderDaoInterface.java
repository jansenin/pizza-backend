package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Order;
import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.exception.DaoException;

import java.util.List;

public interface OrderDaoInterface extends DaoInterface<Order> {
    List<Order> findAllUserOrders(User user) throws DaoException;
}
