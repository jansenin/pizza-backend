package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;

import java.util.List;

public interface PizzaDaoInterface extends DaoInterface<Pizza> {
    List<Pizza> findAllInOrder(int order_id);
    Pizza findPizzaByName(String name);
    boolean addIngredientInPizza(Pizza pizza, Ingredient ingredient);
    boolean removeIngredientFromPizza(Pizza pizza, Ingredient ingredient);
    boolean deleteByName(String name);
}
