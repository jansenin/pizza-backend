package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Order;
import by.fpmibsu.pizza_site.exception.DaoException;

import java.util.List;

public interface OrderDao extends Dao<Order> {
    List<Order> findAllUserOrders(Integer userId) throws DaoException;
    List<Integer> findAllIdOfPizzasForOrder(Integer id) throws DaoException;
}
