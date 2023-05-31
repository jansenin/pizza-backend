package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.TransactionFactoryImpl;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PizzaServiceTest {
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
    void findAllCountTest() throws TransactionException, DaoException {
        PizzaService service = serviceFactory.getService(PizzaService.class);
        List<Pizza> pizzas = service.findAll();
        assertEquals(5, pizzas.size());
    }

    @Test
    void findAllContainsTest() throws TransactionException, DaoException {
        PizzaService service = serviceFactory.getService(PizzaService.class);
        List<Pizza> pizzas = service.findAll();
        List<Ingredient> ingredients = new ArrayList<>(List.of(new Ingredient("лук"),
                new Ingredient("томатный соус"),
                new Ingredient("салями")));
        ingredients.get(0).setId(88);
        ingredients.get(1).setId(91);
        ingredients.get(2).setId(97);
        Pizza pizza = new Pizza("салями ранч", ingredients, 900);
        pizza.setId(70);
        assertTrue(pizzas.contains(pizza));
    }

    @Test
    void findByIdNotNullTest() throws TransactionException, DaoException {
        PizzaService service = serviceFactory.getService(PizzaService.class);
        Pizza pizza = service.findById(68);
        List<Ingredient> ingredients = new ArrayList<>(List.of(new Ingredient("ананас"),
                new Ingredient("курица"),
                new Ingredient("соус песто")));
        ingredients.get(0).setId(89);
        ingredients.get(1).setId(86);
        ingredients.get(2).setId(92);
        Pizza checkPizza = new Pizza("гавайская", ingredients, 1000);
        checkPizza.setId(68);
        assertEquals(checkPizza, pizza);
    }

    @Test
    void findByIdNullTest() throws TransactionException, DaoException {
        PizzaService service = serviceFactory.getService(PizzaService.class);
        Pizza pizza = service.findById(100);
        assertNull(pizza);
    }

    @Test
    void updatePizzaAddRemoveIngredientTest() throws TransactionException, DaoException {
        PizzaService service = serviceFactory.getService(PizzaService.class);
        Pizza pizza = service.findById(68);
        pizza.setName("карибская");
        pizza.setPrice(800);
        service.addIngredientInPizza(pizza, 88);
        service.update(pizza);
        assertEquals(68, pizza.getId());
        assertEquals(service.findById(68), pizza);
        pizza.setName("гавайская");
        service.removeIngredientFromPizza(pizza, 88);
        pizza.setPrice(1000);
        service.update(pizza);
        assertEquals(68, pizza.getId());
        assertEquals(service.findById(68), pizza);
    }

    @Test
    void allowedInsertDeleteTest() throws TransactionException, DaoException {
        PizzaService service = serviceFactory.getService(PizzaService.class);
        List<Ingredient> ingredients = new ArrayList<>(List.of(new Ingredient("курица"),
                new Ingredient("сыр")));
        ingredients.get(0).setId(86);
        ingredients.get(1).setId(85);
        Pizza insertPizza = new Pizza("деревенская", ingredients,700);
        service.insert(insertPizza);
        assertNotEquals(null, insertPizza.getId());
        assertEquals(service.findById(insertPizza.getId()), insertPizza);
        service.deleteById(insertPizza.getId());
        assertNull(service.findById(insertPizza.getId()));
    }

    @Test
    void notAllowedInsertTest() throws TransactionException, DaoException {
        PizzaService service = serviceFactory.getService(PizzaService.class);
        List<Ingredient> ingredients = new ArrayList<>(List.of(new Ingredient("курица"),
                new Ingredient("соус песто")));
        ingredients.get(0).setId(86);
        ingredients.get(0).setId(92);
        Pizza insertPizza = new Pizza("гавайская", ingredients, 1200);
        service.insert(insertPizza);
        assertNull(insertPizza.getId());
    }
}