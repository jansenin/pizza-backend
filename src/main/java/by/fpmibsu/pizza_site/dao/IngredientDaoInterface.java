package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.exception.DaoException;

import java.util.List;

public interface IngredientDaoInterface extends DaoInterface<Ingredient> {
    List<Ingredient> findAllForPizza(Integer pizzaId) throws DaoException;
    Ingredient findIngredientByName(String name) throws DaoException;
    void deleteByIngredient(Ingredient ingredient) throws DaoException;
    void deleteByName(String name) throws DaoException;
}
