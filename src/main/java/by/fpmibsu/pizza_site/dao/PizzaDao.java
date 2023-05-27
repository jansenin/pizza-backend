package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaDao implements PizzaDaoInterface {
    private final Connection connection;
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

    public PizzaDao(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Pizza> findAll() throws DaoException {
        List<Pizza> pizzas = new ArrayList<>();
        try (Statement statement = connection.createStatement();  ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PIZZAS)) {
            IngredientDao ingredientDao = new IngredientDao(connection);
            while (resultSet.next()) {
                int id = resultSet.getInt("pizza_id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                List<Ingredient> ingredients = ingredientDao.findAllForPizza(id);
                pizzas.add(new Pizza(id, name, ingredients, price));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return pizzas;
    }

    @Override
    public List<Pizza> findAllInOrder(int order_id) throws  DaoException {
        List<Pizza> pizzas = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_PIZZAS_IN_ORDER)) {
            statement.setInt(1, order_id);
            resultSet = statement.executeQuery();
            IngredientDao ingredientDao = new IngredientDao(connection);
            while (resultSet.next()) {
                int id = resultSet.getInt("pizza_id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                List<Ingredient> ingredients = ingredientDao.findAllForPizza(id);
                pizzas.add(new Pizza(id, name, ingredients, price));
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
        return pizzas;
    }

    @Override
    public Pizza findById(int id) throws DaoException {
        Pizza pizza = null;
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PIZZA_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            IngredientDao ingredientDao = new IngredientDao(connection);
            while (resultSet.next()) {
                id = resultSet.getInt("pizza_id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                List<Ingredient> ingredients = ingredientDao.findAllForPizza(id);
                pizza = new Pizza(id, name, ingredients, price);
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
        return pizza;
    }

    @Override
    public Pizza findPizzaByName(String name) throws DaoException {
        Pizza pizza = null;
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PIZZA_BY_NAME)) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            IngredientDao ingredientDao = new IngredientDao(connection);
            while (resultSet.next()) {
                int id = resultSet.getInt("pizza_id");
                name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                List<Ingredient> ingredients = ingredientDao.findAllForPizza(id);
                pizza = new Pizza(id, name, ingredients, price);
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
        return pizza;
    }

    @Override
    public Pizza update(Pizza pizza) throws DaoException {
        Pizza idCheckPizza = findById(pizza.getId());
        Pizza nameCheckPizza = findPizzaByName(pizza.getName());
        if (idCheckPizza == null || (nameCheckPizza != null && nameCheckPizza.getId() != pizza.getId())) {
            pizza.setId(Ingredient.ID_NOT_DEFINED);
            return pizza;
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PIZZA)) {
            statement.setString(1, pizza.getName());
            statement.setInt(2, pizza.getPrice());
            statement.setInt(3, pizza.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return pizza;
    }

    @Override
    public boolean addIngredientInPizza(Pizza pizza, Ingredient ingredient) throws DaoException {
        if (pizza.getIngredients().contains(ingredient)) {
            return true;
        }
        int updateRowsCount;
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INGREDIENT_IN_PIZZA)) {
            statement.setInt(1, pizza.getId());
            statement.setInt(2, ingredient.getId());
            updateRowsCount = statement.executeUpdate();
            if (updateRowsCount > 0) {
                pizza.getIngredients().add(ingredient);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean removeIngredientFromPizza(Pizza pizza, Ingredient ingredient) throws DaoException {
        if (!pizza.getIngredients().contains(ingredient)) {
            return true;
        }
        int updateRowsCount;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_INGREDIENT_FROM_PIZZA)) {
            statement.setInt(1, pizza.getId());
            statement.setInt(2, ingredient.getId());
            updateRowsCount = statement.executeUpdate();
            if (updateRowsCount > 0) {
                pizza.getIngredients().remove(ingredient);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean deleteById(int id) throws DaoException {
        int updateRowsCount;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PIZZA_BY_ID)) {
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
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PIZZA_BY_NAME)) {
            statement.setString(1, name);
            updateRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean insert(Pizza pizza) throws DaoException {
        if (findPizzaByName(pizza.getName()) != null) {
            return false;
        }
        int updateRowsCount;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_PIZZA);
            statement.setString(1, pizza.getName());
            statement.setInt(2, pizza.getPrice());
            updateRowsCount = statement.executeUpdate();
            if (updateRowsCount > 0) {
                int pizzaId = findPizzaByName(pizza.getName()).getId();
                pizza.setId(pizzaId);
                for (var i : pizza.getIngredients()) {
                    statement = connection.prepareStatement(SQL_INSERT_INGREDIENT_IN_PIZZA);
                    statement.setInt(1, pizzaId);
                    statement.setInt(2, i.getId());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {}
        }
        return updateRowsCount > 0;
    }
}
