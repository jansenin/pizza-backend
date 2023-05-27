package by.fpmibsu.pizza_site.entity;

public class Ingredient extends Entity {
    private String name;
    static public final int ID_NOT_DEFINED = -1;
    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ingredient [ id = " + id + " name = " + name + " ]";
    }
}
