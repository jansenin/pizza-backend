package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.exception.DaoException;

import java.util.List;

public interface PizzaDaoInterface extends DaoInterface<Pizza> {
    List<Pizza> findAllInOrder(int order_id) throws DaoException;
    Pizza findPizzaByName(String name) throws DaoException;
    boolean addIngredientInPizza(Pizza pizza, Ingredient ingredient) throws DaoException;
    boolean removeIngredientFromPizza(Pizza pizza, Ingredient ingredient) throws DaoException;
    boolean deleteByName(String name) throws DaoException;
}
