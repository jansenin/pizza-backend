package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;

import java.util.List;

public interface IngredientDaoInterface extends DaoInterface {
    List<Ingredient> findAll() throws DaoException;
    Ingredient findIngredientById(int id) throws DaoException;
    Ingredient findIngredientByName(String name) throws DaoException;
    Ingredient update(Ingredient ingredient) throws DaoException;
    boolean deleteByIngredient(Ingredient ingredient) throws DaoException;
    boolean deleteById(int id) throws DaoException;
    boolean deleteByName(String name) throws DaoException;
    boolean insert(Ingredient ingredient) throws DaoException;
}
