package by.fpmibsu.pizza_site.entity;

import java.util.List;

public record Pizza(int id, String name, List<Ingredient> ingredients, int price) {
    static public final int ID_NOT_DEFINED = -1;
}
