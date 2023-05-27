package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao extends BaseDao implements IngredientDaoInterface {
    private static final String SQL_SELECT_ALL_INGREDIENTS = "SELECT * FROM ingredients;";
    private static final String SQL_SELECT_INGREDIENT_BY_ID = "SELECT * FROM ingredients WHERE ingredient_id = ?";
    private static final String SQL_SELECT_INGREDIENT_BY_NAME = "SELECT * FROM ingredients WHERE name = ?";
    private static final String SQL_UPDATE_INGREDIENT = "UPDATE ingredients SET name = ? WHERE ingredient_id = ?";
    private static final String SQL_DELETE_INGREDIENT_BY_ID = "DELETE FROM ingredients WHERE ingredient_id = ?";
    private static final String SQL_DELETE_INGREDIENT_BY_NAME = "DELETE FROM ingredients WHERE name = ?";
    private static final String SQL_INSERT_INGREDIENT = "INSERT INTO ingredients(name) VALUES (?)";
    private static final String SQL_SELECT_ALL_INGREDIENTS_IN_PIZZA = "SELECT ingredient_id, ingredients.name " +
            "FROM ingredients INNER JOIN pizza_ingredients USING(ingredient_id) " +
            "INNER JOIN pizzas USING(pizza_id) " +
            "WHERE pizza_id = ? ";

    public IngredientDao(Connection connection) {
        super(connection);
    }
    @Override
    public List<Ingredient> findAll() throws DaoException {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_INGREDIENTS)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ingredient_id");
                String name = resultSet.getString("name");
                ingredients.add(new Ingredient(id, name));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ingredients;
    }

    @Override
    public List<Ingredient> findAllForPizza(int pizza_id) throws DaoException {
        List<Ingredient> ingredients = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_INGREDIENTS_IN_PIZZA)) {
            statement.setInt(1, pizza_id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ingredient_id");
                String name = resultSet.getString("name");
                ingredients.add(new Ingredient(id, name));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored) {}
        }
        return ingredients;
    }

    @Override
    public Ingredient findById(int id) throws DaoException {
        Ingredient ingredient = null;
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_INGREDIENT_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("ingredient_id");
                String name = resultSet.getString("name");
                ingredient = new Ingredient(id, name);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored) {}
        }
        return ingredient;
    }

    @Override
    public Ingredient findIngredientByName(String name) throws DaoException {
        ResultSet resultSet = null;
        Ingredient ingredient = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_INGREDIENT_BY_NAME)) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ingredient_id");
                name = resultSet.getString("name");
                ingredient = new Ingredient(id, name);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored) {}
        }
        return ingredient;
    }

    @Override
    public Ingredient update(Ingredient ingredient) throws DaoException {
        Ingredient idCheckIngredient = findById(ingredient.getId());
        Ingredient nameCheckIngredient = findIngredientByName(ingredient.getName());
        if (idCheckIngredient == null || (nameCheckIngredient != null && nameCheckIngredient.getId() != ingredient.getId())) {
            ingredient.setId(Ingredient.ID_NOT_DEFINED);
            return ingredient;
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_INGREDIENT)) {
            statement.setString(1, ingredient.getName());
            statement.setInt(2, ingredient.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ingredient;
    }

    @Override
    public boolean deleteByIngredient(Ingredient ingredient) throws DaoException {
        return deleteById(ingredient.getId());
    }

    @Override
    public boolean deleteById(int id) throws DaoException {
        int updateRowsCount;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_INGREDIENT_BY_ID);
            statement.setInt(1, id);
            updateRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean deleteByName(String name) throws DaoException {
        int updateRowsCount;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_INGREDIENT_BY_NAME);
            statement.setString(1, name);
            updateRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean insert(Ingredient ingredient) throws DaoException {
        if (findIngredientByName(ingredient.getName()) != null) {
            return false;
        }
        int updateRowsCount;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INGREDIENT);
            statement.setString(1, ingredient.getName());
            updateRowsCount = statement.executeUpdate();
            if (updateRowsCount > 0) {
                ingredient.setId(findIngredientByName(ingredient.getName()).getId());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return updateRowsCount > 0;
    }
}