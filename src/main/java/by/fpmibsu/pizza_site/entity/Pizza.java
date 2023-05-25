package by.fpmibsu.pizza_site.entity;

import com.sun.security.auth.NTSid;

import java.util.List;

public class Pizza {
    private int id;
    private int price;
    private String name;
    private List<Ingredient> ingredients;
    static public final int ID_NOT_DEFINED = -1;
    public Pizza(int id, String name, List<Ingredient> ingredients, int price) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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
    public String toString() {
        return "Pizza [ id = " + id + " name = " + name + " price = " + price + "ingredients = " + ingredients.toString() + " ]";
    }
}
