package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.database.ConnectionCreator;
import by.fpmibsu.pizza_site.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements OrderDaoInterface {
    private static final String SQL_SELECT_ALL_ORDERS = "SELECT * FROM orders";
    private static final String SQL_SELECT_ALL_USER_ORDERS = "SELECT * FROM orders WHERE user_id = ?";
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id = ?";
    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET status = ?, user_id = ? WHERE order_id = ?";
    private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE order_id = ?";
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders(status, user_id) VALUES(?, ?)";


    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        PizzaDao pizzaDao = new PizzaDao();
        try {
            connection = ConnectionCreator.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDERS);
            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                OrderStatus status = resultSet.getObject("status", OrderStatus.class);
                int user_id = resultSet.getInt("user_id");
                List<Pizza> pizzas = pizzaDao.findAllInOrder(order_id);
                orders.add(new Order(order_id, pizzas, status, user_id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return orders;
    }

    @Override
    public List<Order> findAllUserOrders(User user) throws DaoException {
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        PizzaDao pizzaDao = new PizzaDao();
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_USER_ORDERS);
            statement.setInt(1, user.id());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                OrderStatus status = resultSet.getObject("status", OrderStatus.class);
                int user_id = resultSet.getInt("user_id");
                List<Pizza> pizzas = pizzaDao.findAllInOrder(order_id);
                orders.add(new Order(order_id, pizzas, status, user_id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return orders;
    }

    @Override
    public Order findOrderById(int id) throws DaoException {
        Connection connection = null;
        Order order = null;
        PizzaDao pizzaDao = new PizzaDao();
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                OrderStatus status = resultSet.getObject("status", OrderStatus.class);
                int user_id = resultSet.getInt("user_id");
                List<Pizza> pizzas = pizzaDao.findAllInOrder(order_id);
                order = new Order(order_id, pizzas, status, user_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return order;
    }

    @Override
    public Order update(Order order) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER);
            statement.setObject(1, order.orderStatus());
            statement.setInt(2, order.user_id());
            statement.setInt(3, order.id());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return order;
    }

    @Override
    public boolean deleteByOrder(Order order) throws DaoException {
        return deleteById(order.id());
    }

    @Override
    public boolean deleteById(int id) throws DaoException {
        Connection connection = null;
        int updateRowsCount;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID);
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
    public boolean insert(Order order) throws DaoException {
        Connection connection = null;
        int updateRowsCount;
        try {
            connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ORDER);
            statement.setObject(1, order.orderStatus());
            statement.setInt(2, order.user_id());
            updateRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return updateRowsCount > 0;
    }
}
