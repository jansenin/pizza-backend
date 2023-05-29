package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.OrderDao;
import by.fpmibsu.pizza_site.dao.TransactionImpl;
import by.fpmibsu.pizza_site.entity.Order;
import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public class OrderServiceImpl extends ServiceImpl implements OrderService {
    public OrderServiceImpl(TransactionImpl transaction) {
        super(transaction);
    }

    @Override
    public List<Order> findAll() throws DaoException, TransactionException {
        OrderDao dao = transaction.createDao(OrderDao.class);
        return dao.findAll();
    }

    @Override
    public Order findById(Integer id) throws DaoException, TransactionException {
        OrderDao dao = transaction.createDao(OrderDao.class);
        return dao.findById(id);
    }

    @Override
    public Order update(Order order) throws DaoException, TransactionException {
        OrderDao dao = transaction.createDao(OrderDao.class);
        return dao.update(order);
    }

    @Override
    public void deleteById(Integer id) throws DaoException, TransactionException {
        OrderDao dao = transaction.createDao(OrderDao.class);
        dao.deleteById(id);
    }

    @Override
    public void insert(Order order) throws DaoException, TransactionException {
        OrderDao dao = transaction.createDao(OrderDao.class);
        dao.insert(order);
    }

    @Override
    public List<Order> findAllUserOrders(User user) throws DaoException, TransactionException {
        OrderDao dao = transaction.createDao(OrderDao.class);
        return dao.findAllUserOrders(user);
    }
}
