package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;

import java.util.List;

public interface IngredientDaoInterface {
    List<Ingredient> findAll();
    List<Ingredient> findAllForPizza(int pizza_id);
    Ingredient findIngredientById(int id);
    Ingredient findIngredientByName(String name);
    Ingredient update(Ingredient ingredient);
    boolean deleteByIngredient(Ingredient ingredient);
    boolean deleteById(int id);
    boolean deleteByName(String name);
    boolean insert(Ingredient ingredient);
}
