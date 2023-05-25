import by.fpmibsu.pizza_site.dao.*;
import by.fpmibsu.pizza_site.entity.*;

import java.util.List;


public class Runner {
    public static void main(String[] args) throws DaoException {
        IngredientDao ingredientDao = new IngredientDao();
        Ingredient ingredient = ingredientDao.findIngredientById(1);
        System.out.println(ingredient.toString());
        System.out.println('\n');
        ingredient = new Ingredient(2, "огурец");
        ingredientDao.insert(ingredient);
        ingredient = new Ingredient(3, "колбаса");
        ingredientDao.insert(ingredient);
        List<Ingredient> ingredients = ingredientDao.findAll();
        for (var i : ingredients) {
            System.out.println(i.toString());
        }
        System.out.println('\n');
        ingredient = ingredientDao.findIngredientByName("огурец");
        System.out.println(ingredient.toString());
        System.out.println('\n');
        ingredientDao.deleteByIngredient(ingredient);
        ingredientDao.deleteByName("колбаса");
        ingredients = ingredientDao.findAll();
        for (var i : ingredients) {
            System.out.println(i.toString());
        }
        System.out.println('\n');
        PizzaDao pizzaDao = new PizzaDao();
        List<Pizza> pizzas = pizzaDao.findAll();
        for (var i : pizzas) {
            System.out.println(i.toString());
        }
        System.out.println('\n');
        Pizza pizza = new Pizza(Pizza.ID_NOT_DEFINED, "мясная", List.of(ingredientDao.findIngredientByName("бекон"), ingredientDao.findIngredientByName("курица")), 1300);
        pizzaDao.insert(pizza);
        pizzas = pizzaDao.findAll();
        for (var i : pizzas) {
            System.out.println(i.toString());
        }
        System.out.println('\n');
        pizzaDao.deleteByName("мясная");
        pizzas = pizzaDao.findAll();
        for (var i : pizzas) {
            System.out.println(i.toString());
        }
        System.out.println('\n');
    }
}
