package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;

import java.util.List;

public interface IngredientDaoInterface extends DaoInterface<Ingredient> {
    List<Ingredient> findAllForPizza(int pizza_id);
    Ingredient findIngredientByName(String name);
    boolean deleteByIngredient(Ingredient ingredient);
    boolean deleteByName(String name);
}
