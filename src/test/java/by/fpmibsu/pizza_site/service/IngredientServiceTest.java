package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.TransactionFactory;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientServiceTest {
    static private final ServiceFactoryImpl serviceFactory;
    static {
        try {
            serviceFactory = new ServiceFactoryImpl(new TransactionFactory());
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
        IngredientService service = serviceFactory.getService(IngredientService.class);
        List<Ingredient> ingredients = service.findAll();
        assertEquals(14, ingredients.size());
        assertTrue(ingredients.contains(new Ingredient(89, "ананас")));
        assertTrue(ingredients.contains(new Ingredient(86, "курица")));
    }

    @org.junit.jupiter.api.Test
    void findById() throws TransactionException, DaoException {
        IngredientService service = serviceFactory.getService(IngredientService.class);
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
        IngredientService service = serviceFactory.getService(IngredientService.class);
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
        assertNull(ingredient.getId());

        ingredient = new Ingredient(91, "ананас");
        service.update(ingredient);
        assertNull(ingredient.getId());
    }

    @org.junit.jupiter.api.Test
    void insertDelete() throws TransactionException, DaoException {
        IngredientService service = serviceFactory.getService(IngredientService.class);
        Ingredient insertIngredient = new Ingredient(null, "кокос");
        service.insert(insertIngredient);
        assertNotEquals(null, insertIngredient.getId());
        assertEquals(service.findById(insertIngredient.getId()), insertIngredient);
        service.deleteById(insertIngredient.getId());
        assertNull(service.findById(insertIngredient.getId()));

        insertIngredient = new Ingredient(null, "трюфель");
        service.insert(insertIngredient);
        assertNotEquals(null, insertIngredient.getId());
        assertEquals(service.findById(insertIngredient.getId()), insertIngredient);
        service.deleteById(insertIngredient.getId());
        assertNull(service.findById(insertIngredient.getId()));


        insertIngredient = new Ingredient(null, "ананас");
        service.insert(insertIngredient);
        assertNull(insertIngredient.getId());

        insertIngredient = new Ingredient(null, "сыр");
        service.insert(insertIngredient);
        assertNull(insertIngredient.getId());
    }
}