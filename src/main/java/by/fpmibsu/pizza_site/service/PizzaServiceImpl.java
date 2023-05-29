package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.PizzaDao;
import by.fpmibsu.pizza_site.dao.TransactionImpl;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public class PizzaServiceImpl extends ServiceImpl implements PizzaService {
    public PizzaServiceImpl(TransactionImpl transaction) {
        super(transaction);
    }

    @Override
    public List<Pizza> findAll() throws DaoException, TransactionException {
        PizzaDao dao = transaction.createDao(PizzaDao.class);
        return dao.findAll();
    }

    @Override
    public Pizza findById(Integer id) throws DaoException, TransactionException {
        PizzaDao dao = transaction.createDao(PizzaDao.class);
        return dao.findById(id);
    }

    @Override
    public Pizza update(Pizza pizza) throws DaoException, TransactionException {
        PizzaDao dao = transaction.createDao(PizzaDao.class);
        return dao.update(pizza);
    }

    @Override
    public void deleteById(Integer id) throws DaoException, TransactionException {
        PizzaDao dao = transaction.createDao(PizzaDao.class);
        dao.deleteById(id);
    }

    @Override
    public void insert(Pizza pizza) throws DaoException, TransactionException {
        PizzaDao dao = transaction.createDao(PizzaDao.class);
        dao.insert(pizza);
    }

    @Override
    public void addIngredientInPizza(Pizza pizza, Ingredient ingredient) throws DaoException, TransactionException {
        PizzaDao dao = transaction.createDao(PizzaDao.class);
        dao.addIngredientInPizza(pizza, ingredient);
    }

    @Override
    public void removeIngredientFromPizza(Pizza pizza, Ingredient ingredient) throws DaoException, TransactionException {
        PizzaDao dao = transaction.createDao(PizzaDao.class);
        dao.removeIngredientFromPizza(pizza, ingredient);
    }
}
