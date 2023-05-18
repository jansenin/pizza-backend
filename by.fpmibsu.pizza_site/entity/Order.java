package entity;

import java.util.List;

public record Order(int id, List<Pizza> pizzas) {
}
