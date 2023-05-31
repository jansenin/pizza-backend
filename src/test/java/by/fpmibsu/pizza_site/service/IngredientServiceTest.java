package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.TransactionFactoryImpl;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientServiceTest {
    static private final ServiceFactoryImpl serviceFactory;
    static {
        try {
            serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void closeFactory() throws TransactionException {
        serviceFactory.close();
    }

    @Test
    void findAllSizeTest() throws TransactionException, DaoException {
        IngredientService service = serviceFactory.getService(IngredientService.class);
        List<Ingredient> ingredients = service.findAll();
        assertEquals(14, ingredients.size());
    }

    @ParameterizedTest
    @CsvSource(value = {"89, ананас",
                        "86, курица"})
    void findAllContainsTest(Integer id, String name) throws TransactionException, DaoException {
        IngredientService service = serviceFactory.getService(IngredientService.class);
        List<Ingredient> ingredients = service.findAll();
        Ingredient ingredient = new Ingredient(name);
        ingredient.setId(id);
        assertTrue(ingredients.contains(ingredient));
    }

    @ParameterizedTest
    @CsvSource(value = {"89, ананас",
                        "90, сладкий перец",
                        "91, томатный соус",
                        "92, соус песто"})
    void findByIdNotNullTest(Integer id, String name) throws TransactionException, DaoException {
        IngredientService service = serviceFactory.getService(IngredientService.class);
        Ingredient ingredient = service.findById(id);
        Ingredient checkIngredient = new Ingredient(name);
        checkIngredient.setId(id);
        assertEquals(checkIngredient, ingredient);
    }

    @ParameterizedTest
    @ValueSource(ints = {27, 35, 62, 12})
    void findByIdNullTest(Integer id) throws TransactionException, DaoException {
        IngredientService service = serviceFactory.getService(IngredientService.class);
        Ingredient ingredient = service.findById(id);
        assertNull(ingredient);
    }

    @ParameterizedTest
    @CsvSource(value = {"89, ананас, большой ананас",
                        "90, сладкий перец, кислый перец",
                        "91, томатный соус, помидорный соус",
                        "92, соус песто, соус пистолет"})
    void allowedUpdateTest(Integer id, String oldName, String newName) throws TransactionException, DaoException {
        IngredientService service = serviceFactory.getService(IngredientService.class);
        Ingredient ingredient = service.findById(id);
        ingredient.setName(newName);
        service.update(ingredient);
        assertEquals(id, ingredient.getId());
        assertEquals(newName, ingredient.getName());
        assertEquals(service.findById(id), ingredient);
        ingredient.setName(oldName);
        service.update(ingredient);
        assertEquals(id, ingredient.getId());
        assertEquals(oldName, ingredient.getName());
        assertEquals(service.findById(id), ingredient);
    }

    @ParameterizedTest
    @CsvSource(value = {"30, новое имя",
            "31, новое имя",
            "91, соус песто",
            "92, томатный соус"})
    void notAllowedUpdateTest(Integer id, String name) throws TransactionException, DaoException {
        IngredientService service = serviceFactory.getService(IngredientService.class);
        Ingredient ingredient = new Ingredient(name);
        ingredient.setId(id);
        service.update(ingredient);
        assertNull(ingredient.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"кокос", "трюфель"})
    void allowedInsertDeleteTest(String name) throws TransactionException, DaoException {
        IngredientService service = serviceFactory.getService(IngredientService.class);
        Ingredient insertIngredient = new Ingredient(name);
        service.insert(insertIngredient);
        assertNotNull(insertIngredient.getId());
        assertEquals(service.findById(insertIngredient.getId()), insertIngredient);
        service.deleteById(insertIngredient.getId());
        assertNull(service.findById(insertIngredient.getId()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ананас", "сыр"})
    void notAllowedInsertTest(String name) throws TransactionException, DaoException {
        IngredientService service = serviceFactory.getService(IngredientService.class);
        Ingredient insertIngredient = new Ingredient(name);
        service.insert(insertIngredient);
        assertNull(insertIngredient.getId());
    }

}