package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.database.ConnectionCreator;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaDao implements PizzaDaoInterface {
    private static final String SQL_SELECT_ALL_PIZZAS = "SELECT * FROM pizzas";
    private static final String SQL_SELECT_ALL_PIZZAS_IN_ORDER = "SELECT pizzas.pizza_id, name, price FROM pizzas " +
            "INNER JOIN order_pizzas USING(pizza_id)" +
            "WHERE order_id = ?";
    private static final String SQL_SELECT_PIZZA_BY_ID = "SELECT * FROM pizzas WHERE pizza_id = ?";
    private static final String SQL_SELECT_PIZZA_BY_NAME = "SELECT * FROM pizzas WHERE name = ?";
    private static final String SQL_UPDATE_PIZZA = "UPDATE pizzas SET name = ?, price = ? WHERE pizza_id = ?";
    private static final String SQL_INSERT_INGREDIENT_IN_PIZZA = "INSERT INTO pizza_ingredients(pizza_id, ingredient_id) " +
            "VALUES(?, ?)";
    private static final String SQL_DELETE_INGREDIENT_FROM_PIZZA = "DELETE FROM pizza_ingredients WHERE pizza_id = ? AND " +
            "ingredient_id = ?";
    private static final String SQL_DELETE_PIZZA_BY_ID = "DELETE FROM pizzas WHERE pizza_id = ?";
    private static final String SQL_DELETE_PIZZA_BY_NAME = "DELETE FROM pizzas WHERE name = ?";
    private static final String SQL_INSERT_PIZZA = "INSERT INTO pizzas(name, price) " +
            "VALUES(?, ?)";
    @Override
    public List<Pizza> findAll() throws DaoException {
        List<Pizza> pizzas = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionCreator.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PIZZAS);
            IngredientDao ingredientDao = new IngredientDao();
            while (resultSet.next()) {
                int id = resultSet.getInt("pizza_id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                List<Ingredient> ingredients = ingredientDao.findAllForPizza(id);
                pizzas.add(new Pizza(id, name, ingredients, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return pizzas;
    }

    @Override
    public List<Pizza> findAllInOrder(int order_id) throws DaoException {
        List<Pizza> pizzas = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_PIZZAS_IN_ORDER);
            statement.setInt(1, order_id);
            ResultSet resultSet = statement.executeQuery();
            IngredientDao ingredientDao = new IngredientDao();
            while (resultSet.next()) {
                int id = resultSet.getInt("pizza_id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                List<Ingredient> ingredients = ingredientDao.findAllForPizza(id);
                pizzas.add(new Pizza(id, name, ingredients, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return pizzas;
    }

    @Override
    public Pizza findPizzaById(int id) throws DaoException {
        Pizza pizza = null;
        Connection connection = null;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PIZZA_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            IngredientDao ingredientDao = new IngredientDao();
            while (resultSet.next()) {
                id = resultSet.getInt("pizza_id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                List<Ingredient> ingredients = ingredientDao.findAllForPizza(id);
                pizza = new Pizza(id, name, ingredients, price);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return pizza;
    }

    @Override
    public Pizza findPizzaByName(String name) throws DaoException {
        Pizza pizza = null;
        Connection connection = null;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PIZZA_BY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            IngredientDao ingredientDao = new IngredientDao();
            while (resultSet.next()) {
                int id = resultSet.getInt("pizza_id");
                name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                List<Ingredient> ingredients = ingredientDao.findAllForPizza(id);
                pizza = new Pizza(id, name, ingredients, price);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return pizza;
    }

    @Override
    public Pizza update(Pizza pizza) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PIZZA);
            statement.setString(1, pizza.name());
            statement.setInt(2, pizza.price());
            statement.setInt(3, pizza.id());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return pizza;
    }

    @Override
    public boolean addIngredientInPizza(Pizza pizza, Ingredient ingredient) throws DaoException {
        if (pizza.ingredients().contains(ingredient)) {
            return true;
        }
        Connection connection = null;
        int updateRowsCount;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INGREDIENT_IN_PIZZA);
            statement.setInt(1, pizza.id());
            statement.setInt(2, ingredient.id());
            updateRowsCount = statement.executeUpdate();
            if (updateRowsCount > 0) {
                pizza.ingredients().add(ingredient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean removeIngredientFromPizza(Pizza pizza, Ingredient ingredient) throws DaoException {
        if (!pizza.ingredients().contains(ingredient)) {
            return true;
        }
        Connection connection = null;
        int updateRowsCount;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_INGREDIENT_FROM_PIZZA);
            statement.setInt(1, pizza.id());
            statement.setInt(2, ingredient.id());
            updateRowsCount = statement.executeUpdate();
            if (updateRowsCount > 0) {
                pizza.ingredients().remove(ingredient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean deleteByPizza(Pizza pizza) throws DaoException {
        return deleteById(pizza.id());
    }

    @Override
    public boolean deleteById(int id) throws DaoException {
        Connection connection = null;
        int updateRowsCount;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PIZZA_BY_ID);
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
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PIZZA_BY_NAME);
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
    public boolean insert(Pizza pizza) throws DaoException {
        if (findPizzaByName(pizza.name()).id() != 0) {
            return false;
        }
        Connection connection = null;
        int updateRowsCount;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PIZZA);
            statement.setString(1, pizza.name());
            statement.setInt(2, pizza.price());
            updateRowsCount = statement.executeUpdate();
            if (updateRowsCount > 0) {
                for (var i : pizza.ingredients()) {
                    statement = connection.prepareStatement(SQL_INSERT_INGREDIENT_IN_PIZZA);
                    statement.setInt(1, pizza.id());
                    statement.setInt(2, i.id());
                    statement.executeQuery();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return updateRowsCount > 0;
    }
}
