package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.*;
import by.fpmibsu.pizza_site.exception.DaoException;

import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends BaseDao implements OrderDaoInterface {
    private static final String SQL_SELECT_ALL_ORDERS = "SELECT * FROM orders";
    private static final String SQL_SELECT_ALL_USER_ORDERS = "SELECT * FROM orders WHERE user_id = ?";
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id = ?";
    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET status = CAST (? AS orderstatus), user_id = ? WHERE order_id = ?";
    private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE order_id = ?";
    private static final String SQL_INSERT_PIZZA_IN_ORDER = "INSERT INTO order_pizzas(order_id, pizza_id) VALUES(?, ?)";
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders(status, user_id) VALUES(CAST (? AS orderstatus), ?)";

    private static final Logger logger = Logger.getLogger(IngredientDao.class);

    public OrderDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orders = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDERS)) {
            PizzaDao pizzaDao = new PizzaDao(connection);
            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
                int user_id = resultSet.getInt("user_id");
                List<Pizza> pizzas = pizzaDao.findAllInOrder(order_id);
                orders.add(new Order(order_id, pizzas, status, user_id));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public List<Order> findAllUserOrders(User user) throws DaoException {
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_USER_ORDERS)) {
            statement.setInt(1, user.getId());
            resultSet = statement.executeQuery();
            PizzaDao pizzaDao = new PizzaDao(connection);
            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
                int user_id = resultSet.getInt("user_id");
                List<Pizza> pizzas = pizzaDao.findAllInOrder(order_id);
                orders.add(new Order(order_id, pizzas, status, user_id));
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
        return orders;
    }

    @Override
    public Order findById(Integer id) throws DaoException {
        ResultSet resultSet = null;
        Order order = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            PizzaDao pizzaDao = new PizzaDao(connection);
            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
                int user_id = resultSet.getInt("user_id");
                List<Pizza> pizzas = pizzaDao.findAllInOrder(order_id);
                order = new Order(order_id, pizzas, status, user_id);
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
        return order;
    }

    @Override
    public Order update(Order order) throws DaoException {
        Order checkOrder = findById(order.getId());
        if (checkOrder == null) {
            order.setId(null);
            logger.info("attempt to update order with no existing id");
            return order;
        }
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER)) {
            statement.setString(1, order.getOrderStatus().toString());
            statement.setInt(2, order.getUserId());
            statement.setInt(3, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return order;
    }
    @Override
    public void deleteById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void insert(Order order) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, order.getOrderStatus().toString());
            statement.setInt(2, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                    int orderId = resultSet.getInt(1);
                    order.setId(orderId);
                for (var i : order.getPizzas()) {
                    statement = connection.prepareStatement(SQL_INSERT_PIZZA_IN_ORDER);
                    statement.setInt(1, orderId);
                    statement.setInt(2, i.getId());
                    statement.executeUpdate();
                }
            } else {
                logger.error("There is no auto incremented index after trying to add record into table `orders`");
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
