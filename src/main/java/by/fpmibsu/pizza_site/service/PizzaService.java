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
    public void deleteById(int id) throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        dao.deleteById(id);
    }

    @Override
    public void insert(Pizza pizza) throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        dao.insert(pizza);
    }

    @Override
    public List<Pizza> findAllInOrder(int order_id) throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        return dao.findAllInOrder(order_id);
    }

    @Override
    public void addIngredientInPizza(Pizza pizza, Ingredient ingredient) throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        dao.addIngredientInPizza(pizza, ingredient);
    }

    @Override
    public void removeIngredientFromPizza(Pizza pizza, Ingredient ingredient) throws DaoException, TransactionException {
        PizzaDaoInterface dao = transaction.createDao(PizzaDaoInterface.class);
        dao.removeIngredientFromPizza(pizza, ingredient);
    }
}
