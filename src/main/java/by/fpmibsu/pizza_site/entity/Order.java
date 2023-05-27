package by.fpmibsu.pizza_site.entity;

import java.util.List;

public class Order extends Entity {
    private int userId;
    private OrderStatus orderStatus;
    private List<Pizza> pizzas;
    static public final int ID_NOT_DEFINED = -1;
  
    public Order(int id, List<Pizza> pizzas, OrderStatus orderStatus, int userId) {
        this.id = id;
        this.userId = userId;
        this.pizzas = pizzas;
        this.orderStatus = orderStatus;
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

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order [ orderId = " + id + " userId = " + userId + " orderStatus = " + orderStatus.toString() + " pizzas = " + pizzas.toString() + " ]";
    }
}
