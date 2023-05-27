package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.OrderDaoInterface;
import by.fpmibsu.pizza_site.dao.Transaction;
import by.fpmibsu.pizza_site.entity.Order;
import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public class OrderService extends Service implements OrderServiceInterface {
    public OrderService(Transaction transaction) {
        super(transaction);
    }

    @Override
    public List<Order> findAll() throws DaoException, TransactionException {
        OrderDaoInterface dao = transaction.createDao(OrderDaoInterface.class);
        return dao.findAll();
    }

    @Override
    public Order findById(int id) throws DaoException, TransactionException {
        OrderDaoInterface dao = transaction.createDao(OrderDaoInterface.class);
        return dao.findById(id);
    }

    @Override
    public Order update(Order order) throws DaoException, TransactionException {
        OrderDaoInterface dao = transaction.createDao(OrderDaoInterface.class);
        return dao.update(order);
    }

    @Override
    public void deleteById(int id) throws DaoException, TransactionException {
        OrderDaoInterface dao = transaction.createDao(OrderDaoInterface.class);
        dao.deleteById(id);
    }

    @Override
    public void insert(Order order) throws DaoException, TransactionException {
        OrderDaoInterface dao = transaction.createDao(OrderDaoInterface.class);
        dao.insert(order);
    }

    @Override
    public List<Order> findAllUserOrders(User user) throws DaoException, TransactionException {
        OrderDaoInterface dao = transaction.createDao(OrderDaoInterface.class);
        return dao.findAllUserOrders(user);
    }
}
