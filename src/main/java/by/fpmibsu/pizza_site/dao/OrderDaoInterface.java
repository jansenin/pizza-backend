package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Order;
import by.fpmibsu.pizza_site.entity.User;

import java.util.List;

public interface OrderDaoInterface {
    List<Order> findAll();
    List<Order> findAllUserOrders(User user);
    Order findOrderById(int id);
    Order update(Order order);
    boolean deleteById(int id);
    boolean insert(Order order);
}
