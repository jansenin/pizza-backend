package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;

import java.util.List;

public interface PizzaDaoInterface extends DaoInterface {
    List<Pizza> findAll() throws DaoException;
    List<Pizza> findAllInOrder(int order_id) throws DaoException;
    Pizza findPizzaById(int id) throws DaoException;
    Pizza findPizzaByName(String name) throws DaoException;
    Pizza update(Pizza pizza) throws DaoException;
    boolean addIngredientInPizza(Pizza pizza, Ingredient ingredient) throws DaoException;
    boolean removeIngredientFromPizza(Pizza pizza, Ingredient ingredient) throws DaoException;
    boolean deleteById(int id) throws DaoException;
    boolean deleteByName(String name) throws DaoException;
    boolean insert(Pizza pizza) throws DaoException;
}
