package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public interface PizzaServiceInterface extends ServiceInterface {
    List<Pizza> findAll() throws DaoException, TransactionException;
    Pizza findById(int id) throws DaoException, TransactionException;
    Pizza update(Pizza pizza) throws DaoException, TransactionException;
    boolean deleteById(int id) throws DaoException, TransactionException;
    boolean insert(Pizza pizza) throws DaoException, TransactionException;
    List<Pizza> findAllInOrder(int order_id) throws DaoException, TransactionException;
    boolean addIngredientInPizza(Pizza pizza, Ingredient ingredient) throws DaoException, TransactionException;
    boolean removeIngredientFromPizza(Pizza pizza, Ingredient ingredient) throws DaoException, TransactionException;
}
