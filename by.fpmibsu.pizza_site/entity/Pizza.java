package entity;

import java.util.List;

public record Pizza(int id, String name, List<Ingredient> ingredients, int price) {
}
