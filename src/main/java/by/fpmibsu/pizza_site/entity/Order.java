package by.fpmibsu.pizza_site.entity;

import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private OrderStatus orderStatus;
    private List<Pizza> pizzas;
    static public final int ID_NOT_DEFINED = -1;
    public Order(int orderId, List<Pizza> pizzas, OrderStatus orderStatus, int userId) {
        this.orderId = orderId;
        this.userId = userId;
        this.pizzas = pizzas;
        this.orderStatus = orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order [ orderId = " + orderId + " userId = " + userId + " orderStatus = " + orderStatus.toString() + "pizzas = " + pizzas.toString() + " ]";
    }
}
