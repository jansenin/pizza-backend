package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Entity;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.exception.DaoException;

import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaDaoImpl extends BaseDaoImpl implements PizzaDao {
    private static final String SQL_SELECT_ALL_PIZZAS = "SELECT * FROM pizzas";
    private static final String SQL_SELECT_ALL_ID_OF_INGREDIENTS_FOR_PIZZA = "SELECT ingredient_id FROM pizza_ingredients " +
            "WHERE pizza_id = ?";
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

    private static final Logger logger = Logger.getLogger(IngredientDaoImpl.class);

    public PizzaDaoImpl(Connection connection) {
        super(connection);
    }



    @Override
    public List<Pizza> findAll() throws DaoException {
        List<Pizza> pizzas = new ArrayList<>();
        try (Statement statement = connection.createStatement();  ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PIZZAS)) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("pizza_id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                List<Integer> ingredientsId = findAllIngredientsIdForPizza(id);
                List<Ingredient> ingredients = new ArrayList<>();
                for (Integer i : ingredientsId) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(i);
                    ingredients.add(ingredient);
                }
                Pizza pizza = new Pizza(name, ingredients, price);
                pizza.setId(id);
                pizzas.add(pizza);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return pizzas;
    }

    @Override
    public List<Integer> findAllIngredientsIdForPizza(Integer id) throws DaoException {
        List<Integer> ids = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ID_OF_INGREDIENTS_FOR_PIZZA)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getInt("ingredient_id"));
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
        return ids;
    }

    @Override
    public Pizza findById(Integer id) throws DaoException {
        Pizza pizza = null;
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PIZZA_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("pizza_id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                List<Integer> ingredientsId = findAllIngredientsIdForPizza(id);
                List<Ingredient> ingredients = new ArrayList<>();
                for (Integer i : ingredientsId) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(i);
                    ingredients.add(ingredient);
                }
                pizza = new Pizza(name, ingredients, price);
                pizza.setId(id);
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
            while (resultSet.next()) {
                int id = resultSet.getInt("pizza_id");
                name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                List<Integer> ingredientsId = findAllIngredientsIdForPizza(id);
                List<Ingredient> ingredients = new ArrayList<>();
                for (Integer i : ingredientsId) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(i);
                    ingredients.add(ingredient);
                }
                pizza = new Pizza(name, ingredients, price);
                pizza.setId(id);
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
        if (idCheckPizza == null) {
            logger.info("attempt to update pizza with no existing id");
            pizza.setId(null);
            return pizza;
        }
        if (nameCheckPizza != null && !nameCheckPizza.getId().equals(pizza.getId())) {
            logger.info("attempt to set pizza already existing name");
            pizza.setId(null);
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
    public void addIngredientInPizza(Pizza pizza, Integer ingredientId) throws DaoException {
        if (pizza.getIngredients().stream().map(Entity::getId).toList().contains(ingredientId)) {
            return;
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INGREDIENT_IN_PIZZA, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, pizza.getId());
            statement.setInt(2, ingredientId);
            statement.executeUpdate();
            Ingredient ingredient = new Ingredient();
            ingredient.setId(ingredientId);
            pizza.getIngredients().add(ingredient);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void removeIngredientFromPizza(Pizza pizza, Integer ingredientId) throws DaoException {
        if (!pizza.getIngredients().stream().map(Entity::getId).toList().contains(ingredientId)) {
            return;
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_INGREDIENT_FROM_PIZZA)) {
            statement.setInt(1, pizza.getId());
            statement.setInt(2, ingredientId);
            statement.executeUpdate();
            pizza.getIngredients().removeIf(i -> i.getId().equals(ingredientId));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PIZZA_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteByName(String name) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PIZZA_BY_NAME)) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void insert(Pizza pizza) throws DaoException {
        if (findPizzaByName(pizza.getName()) != null) {
            logger.info("Attempt to add existing pizza " + pizza.getName());
            pizza.setId(null);
            return;
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_PIZZA, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, pizza.getName());
            statement.setInt(2, pizza.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int pizzaId = resultSet.getInt(1);
                pizza.setId(pizzaId);
                for (var i : pizza.getIngredients()) {
                    statement = connection.prepareStatement(SQL_INSERT_INGREDIENT_IN_PIZZA);
                    statement.setInt(1, pizzaId);
                    statement.setInt(2, i.getId());
                    statement.executeUpdate();
                }
            } else {
                logger.error("There is no auto incremented index after trying to add record into table `pizzas`");
                throw new DaoException();
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
    }
}
