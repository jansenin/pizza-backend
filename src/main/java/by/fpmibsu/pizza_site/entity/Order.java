package by.fpmibsu.pizza_site.entity;

import java.util.List;

public record Order(int id, List<Pizza> pizzas, OrderStatus orderStatus, int user_id) {
}
