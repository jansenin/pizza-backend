package by.fpmibsu.pizza_site.entity;

import java.util.Objects;

public class Ingredient extends Entity {
    private String name;
    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        Ingredient toCompare = (Ingredient) object;
        return name.equals(toCompare.name);
    }

    @Override
    public String toString() {
        return "Ingredient [ id = " + id + " name = " + name + " ]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
