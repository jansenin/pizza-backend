package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IngredientDaoInterface {
    List<Ingredient> findAll() throws DaoException;
    Ingredient findIngredientById(int id) throws DaoException;
    Ingredient update(Ingredient ingredient) throws DaoException;
    boolean deleteByIngredient(Ingredient ingredient) throws DaoException;
    boolean deleteById(int id) throws DaoException;
    int insert(Ingredient ingredient) throws DaoException;
    default void close(Connection connection) throws DaoException{
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
