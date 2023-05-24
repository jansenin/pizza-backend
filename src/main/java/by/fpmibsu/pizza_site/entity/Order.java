package by.fpmibsu.pizza_site.entity;

import java.util.List;

public record Order(int id, List<Pizza> pizzas, OrderStatus orderStatus, int user_id) {
    static public final int ID_NOT_DEFINED = -1;
}
