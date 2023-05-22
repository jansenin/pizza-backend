package by.fpmibsu.pizza_site;

import by.fpmibsu.pizza_site.dao.DaoException;
import by.fpmibsu.pizza_site.dao.IngredientDao;
import by.fpmibsu.pizza_site.entity.Ingredient;

import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException {
        IngredientDao ingredientDao = new IngredientDao();
        Ingredient ingredient = ingredientDao.findIngredientById(1);
        System.out.println(ingredient.name());
    }
}