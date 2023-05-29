package by.fpmibsu.pizza_site.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Pizza extends Entity {
    private int price;
    private String name;
    private List<Ingredient> ingredients;
    public Pizza(String name, List<Ingredient> ingredients, int price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        Pizza toCompare = (Pizza) object;
        return name.equals(toCompare.name)
                && price == toCompare.price
                && new HashSet<>(ingredients).equals(new HashSet<>(toCompare.ingredients));
    }

    @Override
    public String toString() {
        return "Pizza [ id = " + id + " name = " + name + " price = " + price + " ingredients = " + ingredients.toString() + " ]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), price, name);
    }
}
