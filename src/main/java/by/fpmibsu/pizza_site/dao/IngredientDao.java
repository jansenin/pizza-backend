package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;

import by.fpmibsu.pizza_site.exception.DaoException;
import org.apache.log4j.Logger;

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


    private static final Logger logger = Logger.getLogger(IngredientDao.class);

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
    public List<Ingredient> findAllForPizza(Integer pizzaId) throws DaoException {
        List<Ingredient> ingredients = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_INGREDIENTS_IN_PIZZA)) {
            statement.setInt(1, pizzaId);
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
    public Ingredient findById(Integer id) throws DaoException {
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
        if (idCheckIngredient == null) {
            logger.info("attempt to update ingredient with no existing id");
            ingredient.setId(null);
            return ingredient;
        }
        if (nameCheckIngredient != null && !nameCheckIngredient.getId().equals(ingredient.getId())) {
            logger.info("attempt to set ingredient already existing name");
            ingredient.setId(null);
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
    public void deleteByIngredient(Ingredient ingredient) throws DaoException {
        deleteById(ingredient.getId());
    }

    @Override
    public void deleteById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_INGREDIENT_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteByName(String name) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_INGREDIENT_BY_NAME)) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void insert(Ingredient ingredient) throws DaoException {
        if (findIngredientByName(ingredient.getName()) != null) {
            ingredient.setId(null);
            logger.info("Attempt to add existing ingredient " + ingredient.getName());
            return;
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INGREDIENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, ingredient.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                ingredient.setId(resultSet.getInt(1));
            } else {
                logger.error("There is no auto incremented index after trying to add record into table `ingredients`");
                throw new DaoException();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}