package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements OrderDaoInterface {
    private final Connection connection;
    private static final String SQL_SELECT_ALL_ORDERS = "SELECT * FROM orders";
    private static final String SQL_SELECT_ALL_USER_ORDERS = "SELECT * FROM orders WHERE user_id = ?";
    private static final String SQL_SELECT_ID_OF_LAST_USER_ORDER = "SELECT MAX(order_id) AS order_id FROM orders WHERE user_id = ?";
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id = ?";
    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET status = CAST (? AS orderstatus), user_id = ? WHERE order_id = ?";
    private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE order_id = ?";
    private static final String SQL_INSERT_PIZZA_IN_ORDER = "INSERT INTO order_pizzas(order_id, pizza_id) VALUES(?, ?)";
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders(status, user_id) VALUES(CAST (? AS orderstatus), ?)";

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDERS);
            PizzaDao pizzaDao = new PizzaDao(connection);
            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
                int user_id = resultSet.getInt("user_id");
                List<Pizza> pizzas = pizzaDao.findAllInOrder(order_id);
                orders.add(new Order(order_id, pizzas, status, user_id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public List<Order> findAllUserOrders(User user) {
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_USER_ORDERS);
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            PizzaDao pizzaDao = new PizzaDao(connection);
            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
                int user_id = resultSet.getInt("user_id");
                List<Pizza> pizzas = pizzaDao.findAllInOrder(order_id);
                orders.add(new Order(order_id, pizzas, status, user_id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public Order findOrderById(int id) {
        Order order = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            PizzaDao pizzaDao = new PizzaDao(connection);
            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
                int user_id = resultSet.getInt("user_id");
                List<Pizza> pizzas = pizzaDao.findAllInOrder(order_id);
                order = new Order(order_id, pizzas, status, user_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public Order update(Order order) {
        Order checkOrder = findOrderById(order.getOrderId());
        if (checkOrder == null) {
            order.setOrderId(Order.ID_NOT_DEFINED);
            return order;
        }
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER);
            statement.setString(1, order.getOrderStatus().toString());
            statement.setInt(2, order.getUserId());
            statement.setInt(3, order.getOrderId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }
    @Override
    public boolean deleteById(int id) {
        int updateRowsCount;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID);
            statement.setInt(1, id);
            updateRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updateRowsCount > 0;
    }

    @Override
    public boolean insert(Order order) {
        int updateRowsCount;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ORDER);
            statement.setString(1, order.getOrderStatus().toString());
            statement.setInt(2, order.getUserId());
            updateRowsCount = statement.executeUpdate();
            if (updateRowsCount > 0) {
                statement = connection.prepareStatement(SQL_SELECT_ID_OF_LAST_USER_ORDER);
                statement.setInt(1, order.getUserId());
                ResultSet resultSet = statement.executeQuery();
                int orderId = 0;
                while (resultSet.next()) {
                    orderId = resultSet.getInt("order_id");
                    order.setOrderId(orderId);
                }
                for (var i : order.getPizzas()) {
                    statement = connection.prepareStatement(SQL_INSERT_PIZZA_IN_ORDER);
                    statement.setInt(1, orderId);
                    statement.setInt(2, i.getId());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updateRowsCount > 0;
    }
}
