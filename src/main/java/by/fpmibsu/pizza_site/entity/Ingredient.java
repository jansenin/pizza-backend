package by.fpmibsu.pizza_site.entity;

public class Ingredient {
    private int id;
    private String name;
    static public final int ID_NOT_DEFINED = -1;
    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ingredient [ id = " + id + " name = " + name + " ]";
    }
}
