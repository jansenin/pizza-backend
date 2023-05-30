package by.fpmibsu.pizza_site.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Order extends Entity {
    private Integer userId;
    private OrderStatus orderStatus;
    private List<Pizza> pizzas;

    public Order() {}

    public Order(List<Pizza> pizzas, OrderStatus orderStatus, Integer userId) {
        this.userId = userId;
        this.pizzas = pizzas;
        this.orderStatus = orderStatus;
    }

    public Integer getUserId() {
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
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        Order toCompare = (Order) object;
        return id.equals(toCompare.id)
                && userId.equals(toCompare.userId)
                && orderStatus.equals(toCompare.orderStatus)
                && new HashSet<>(pizzas).equals(new HashSet<>(toCompare.pizzas));
    }

    @Override
    public String toString() {
        return "Order [ orderId = " + id + " userId = " + userId + " orderStatus = " + orderStatus.toString() + " pizzas = " + pizzas.toString() + " ]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, orderStatus);
    }
}
