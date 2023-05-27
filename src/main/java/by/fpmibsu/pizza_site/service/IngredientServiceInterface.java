package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public interface IngredientServiceInterface extends ServiceInterface {
    List<Ingredient> findAll() throws TransactionException, DaoException;
    Ingredient findById(int id) throws TransactionException, DaoException;
    Ingredient update(Ingredient ingredient) throws TransactionException, DaoException;
    void deleteById(int id) throws TransactionException, DaoException;
    void insert(Ingredient ingredient) throws TransactionException, DaoException;
    void deleteByIngredient(Ingredient ingredient) throws TransactionException, DaoException;
}
