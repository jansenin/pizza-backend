package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.exception.DaoException;

public interface IngredientDao extends Dao<Ingredient> {
    Ingredient findIngredientByName(String name) throws DaoException;
    void deleteByIngredient(Ingredient ingredient) throws DaoException;
    void deleteByName(String name) throws DaoException;
}
