package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.PizzaDaoInterface;
import by.fpmibsu.pizza_site.dao.Transaction;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public class PizzaService extends Service implements PizzaServiceInterface {
    public PizzaService(Transaction transaction) {
        super(transaction);
    }

    @Override
    public List<Pizza> findAll() throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        return dao.findAll();
    }

    @Override
    public Pizza findById(int id) throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        return dao.findById(id);
    }

    @Override
    public Pizza update(Pizza pizza) throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        return dao.update(pizza);
    }

    @Override
    public boolean deleteById(int id) throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        return dao.deleteById(id);
    }

    @Override
    public boolean insert(Pizza pizza) throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        return dao.insert(pizza);
    }

    @Override
    public List<Pizza> findAllInOrder(int order_id) throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        return dao.findAllInOrder(order_id);
    }

    @Override
    public boolean addIngredientInPizza(Pizza pizza, Ingredient ingredient) throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        return dao.addIngredientInPizza(pizza, ingredient);
    }

    @Override
    public boolean removeIngredientFromPizza(Pizza pizza, Ingredient ingredient) throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        return dao.removeIngredientFromPizza(pizza, ingredient);
    }
}
