package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;

import java.util.List;

public interface PizzaDaoInterface {
    List<Pizza> findAll();
    List<Pizza> findAllInOrder(int order_id);
    Pizza findPizzaById(int id);
    Pizza findPizzaByName(String name);
    Pizza update(Pizza pizza);
    boolean addIngredientInPizza(Pizza pizza, Ingredient ingredient);
    boolean removeIngredientFromPizza(Pizza pizza, Ingredient ingredient);
    boolean deleteById(int id);
    boolean deleteByName(String name);
    boolean insert(Pizza pizza);
}
