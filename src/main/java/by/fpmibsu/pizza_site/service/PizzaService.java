package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public interface PizzaService extends Service {
    List<Pizza> findAll() throws DaoException, TransactionException;
    Pizza findById(Integer id) throws DaoException, TransactionException;
    Pizza update(Pizza pizza) throws DaoException, TransactionException;
    void deleteById(Integer id) throws DaoException, TransactionException;
    void insert(Pizza pizza) throws DaoException, TransactionException;
    void addIngredientInPizza(Pizza pizza, Ingredient ingredient) throws DaoException, TransactionException;
    void removeIngredientFromPizza(Pizza pizza, Ingredient ingredient) throws DaoException, TransactionException;
}
