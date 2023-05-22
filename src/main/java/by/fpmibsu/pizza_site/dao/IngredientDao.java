package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.database.ConnectionCreator;
import by.fpmibsu.pizza_site.entity.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao implements IngredientDaoInterface {
    private static final String SQL_SELECT_ALL_INGREDIENTS = "SELECT * FROM ingredients;";
    private static final String SQL_SELECT_INGREDIENT_BY_ID = "SELECT * FROM ingredients WHERE ingredient_id = ?";
    private static final String SQL_SELECT_INGREDIENT_BY_NAME = "SELECT * FROM ingredients WHERE name = ?";
    private static final String SQL_UPDATE_INGREDIENT = "UPDATE ingredients SET ingredient_id = ?, name = ? WHERE ingredient_id = ?";
    private static final String SQL_DELETE_INGREDIENT_BY_ID = "DELETE FROM ingredients WHERE ingredient_id = ?";
    private static final String SQL_DELETE_INGREDIENT_BY_NAME = "DELETE FROM ingredients WHERE ingredient_id = ?";
    private static final String SQL_INSERT_INGREDIENT = "INSERT INTO ingredients VALUES (?, ?)";

    @Override
    public List<Ingredient> findAll() throws DaoException {
        List<Ingredient> ingredients = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionCreator.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_INGREDIENTS);
            while (resultSet.next()) {
                int id = resultSet.getInt("ingredient_id");
                String name = resultSet.getString("name");
                ingredients.add(new Ingredient(id, name, 0));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return ingredients;
    }

    @Override
    public Ingredient findIngredientById(int id) throws DaoException {
        Connection connection = null;
        String name = null;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_INGREDIENT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("ingredient_id");
                name = resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return new Ingredient(id, name, 0);
    }

    @Override
    public Ingredient findIngredientByName(String name) throws DaoException {
        Connection connection = null;
        int id = 0;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_INGREDIENT_BY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("ingredient_id");
                name = resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return new Ingredient(id, name, 0);
    }

    @Override
    public Ingredient update(Ingredient ingredient) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_INGREDIENT);

            statement.setInt(1, ingredient.id());
            statement.setString(2, ingredient.name());
            statement.setInt(3, ingredient.id());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return ingredient;
    }

    @Override
    public boolean deleteByIngredient(Ingredient ingredient) throws DaoException {
        Connection connection = null;
        int updateRowsCount;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_INGREDIENT_BY_ID);
            statement.setInt(1, ingredient.id());
            updateRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean deleteById(int id) throws DaoException {
        Connection connection = null;
        int updateRowsCount;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_INGREDIENT_BY_ID);
            statement.setInt(1, id);
            updateRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean deleteByName(String name) throws DaoException {
        Connection connection = null;
        int updateRowsCount;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_INGREDIENT_BY_NAME);
            statement.setString(1, name);
            updateRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean insert(Ingredient ingredient) throws DaoException {
        Connection connection = null;
        int updateRowsCount;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INGREDIENT);
            statement.setInt(1, ingredient.id());
            statement.setString(2, ingredient.name());
            updateRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return updateRowsCount > 0;
    }
}