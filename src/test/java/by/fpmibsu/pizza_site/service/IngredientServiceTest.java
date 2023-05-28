package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.TransactionFactory;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientServiceTest {
    static private final ServiceFactory serviceFactory;
    static {
        try {
            serviceFactory = new ServiceFactory(new TransactionFactory());
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.jupiter.api.AfterAll
    static void closeFactory() throws TransactionException {
        serviceFactory.close();
    }

    @org.junit.jupiter.api.Test
    void findAll() throws TransactionException, DaoException {
        IngredientServiceInterface service = serviceFactory.getService(IngredientServiceInterface.class);
        List<Ingredient> ingredients = service.findAll();
        assertEquals(14, ingredients.size());
        assertTrue(ingredients.contains(new Ingredient(89, "ананас")));
        assertTrue(ingredients.contains(new Ingredient(86, "курица")));
    }

    @org.junit.jupiter.api.Test
    void findById() throws TransactionException, DaoException {
        IngredientServiceInterface service = serviceFactory.getService(IngredientServiceInterface.class);
        Ingredient ingredient = service.findById(89);
        assertEquals(new Ingredient(89, "ананас"), ingredient);
        ingredient = service.findById(90);
        assertEquals(new Ingredient(90, "сладкий перец"), ingredient);
        ingredient = service.findById(91);
        assertEquals(new Ingredient(91, "томатный соус"), ingredient);
        ingredient = service.findById(92);
        assertEquals(new Ingredient(92, "соус песто"), ingredient);
        ingredient = service.findById(14);
        assertNull(ingredient);
        ingredient = service.findById(23);
        assertNull(ingredient);
    }

    @org.junit.jupiter.api.Test
    void update() throws TransactionException, DaoException {
        IngredientServiceInterface service = serviceFactory.getService(IngredientServiceInterface.class);
        Ingredient ingredient = service.findById(86);
        ingredient.setName("копчёная курица");
        service.update(ingredient);
        assertEquals(86, ingredient.getId());
        assertEquals("копчёная курица", ingredient.getName());
        assertEquals(service.findById(86), ingredient);
        ingredient.setName("курица");
        service.update(ingredient);
        assertEquals(86, ingredient.getId());
        assertEquals("курица", ingredient.getName());
        assertEquals(service.findById(86), ingredient);

        ingredient = new Ingredient(17, "несуществующий");
        service.update(ingredient);
        assertEquals(Ingredient.ID_NOT_DEFINED, ingredient.getId());

        ingredient = new Ingredient(91, "ананас");
        service.update(ingredient);
        assertEquals(Ingredient.ID_NOT_DEFINED, ingredient.getId());
    }

    @org.junit.jupiter.api.Test
    void insertDelete() throws TransactionException, DaoException {
        IngredientServiceInterface service = serviceFactory.getService(IngredientServiceInterface.class);
        Ingredient insertIngredient = new Ingredient(Ingredient.ID_NOT_DEFINED, "кокос");
        service.insert(insertIngredient);
        assertNotEquals(Ingredient.ID_NOT_DEFINED, insertIngredient.getId());
        assertEquals(service.findById(insertIngredient.getId()), insertIngredient);
        service.deleteById(insertIngredient.getId());
        assertNull(service.findById(insertIngredient.getId()));

        insertIngredient = new Ingredient(Ingredient.ID_NOT_DEFINED, "трюфель");
        service.insert(insertIngredient);
        assertNotEquals(Ingredient.ID_NOT_DEFINED, insertIngredient.getId());
        assertEquals(service.findById(insertIngredient.getId()), insertIngredient);
        service.deleteById(insertIngredient.getId());
        assertNull(service.findById(insertIngredient.getId()));


        insertIngredient = new Ingredient(Ingredient.ID_NOT_DEFINED, "ананас");
        service.insert(insertIngredient);
        assertEquals(Ingredient.ID_NOT_DEFINED, insertIngredient.getId());

        insertIngredient = new Ingredient(Ingredient.ID_NOT_DEFINED, "сыр");
        service.insert(insertIngredient);
        assertEquals(Ingredient.ID_NOT_DEFINED, insertIngredient.getId());
    }
}