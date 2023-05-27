package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.exception.DaoException;

import java.util.List;

public interface IngredientDaoInterface extends DaoInterface<Ingredient> {
    List<Ingredient> findAllForPizza(int pizza_id) throws DaoException;
    Ingredient findIngredientByName(String name) throws DaoException;
    boolean deleteByIngredient(Ingredient ingredient) throws DaoException;
    boolean deleteByName(String name) throws DaoException;
}
