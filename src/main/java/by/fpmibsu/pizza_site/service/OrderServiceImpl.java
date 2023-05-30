package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.IngredientDao;
import by.fpmibsu.pizza_site.dao.OrderDao;
import by.fpmibsu.pizza_site.dao.PizzaDao;
import by.fpmibsu.pizza_site.dao.TransactionImpl;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Order;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public class OrderServiceImpl extends ServiceImpl implements OrderService {
    public OrderServiceImpl(TransactionImpl transaction) {
        super(transaction);
    }

    private void LoadData(Order order) throws TransactionException, DaoException {
        if (order == null) {
            return;
        }
        PizzaDao pizzaDao = transaction.createDao(PizzaDao.class);
        IngredientDao ingredientDao = transaction.createDao(IngredientDao.class);
        for (int i = 0; i < order.getPizzas().size(); ++i) {
            order.getPizzas().set(i, pizzaDao.findById(order.getPizzas().get(i).getId()));
        }
        for (Pizza pizza : order.getPizzas()) {
            for (Ingredient ingredient : pizza.getIngredients()) {
                ingredient.setName(ingredientDao.findById(ingredient.getId()).getName());
            }
        }
    }

    @Override
    public List<Order> findAll() throws DaoException, TransactionException {
        OrderDao orderDao = transaction.createDao(OrderDao.class);
        List<Order> orders = orderDao.findAll();
        for (Order order : orders) {
            LoadData(order);
        }
        return orders;
    }

    @Override
    public Order findById(Integer id) throws DaoException, TransactionException {
        OrderDao orderDao = transaction.createDao(OrderDao.class);
        Order order = orderDao.findById(id);
        LoadData(order);
        return order;
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
    public List<Order> findAllUserOrders(Integer userId) throws DaoException, TransactionException {
        OrderDao orderDao = transaction.createDao(OrderDao.class);
        List<Order> orders = orderDao.findAllUserOrders(userId);
        for (Order order : orders) {
            LoadData(order);
        }
        return orders;
    }
}
