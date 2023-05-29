package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.TransactionFactory;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PizzaServiceTest {
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

    @Test
    void findAll() throws TransactionException, DaoException {
        PizzaService service = serviceFactory.getService(PizzaService.class);
        List<Pizza> pizzas = service.findAll();
        assertEquals(5, pizzas.size());
        assertTrue(pizzas.contains(new Pizza(70, "салями ранч", List.of(new Ingredient(88, "лук"),
                new Ingredient(91, "томатный соус"),
                new Ingredient(97, "салями")), 900)));
    }

    @Test
    void findById() throws TransactionException, DaoException {
        PizzaService service = serviceFactory.getService(PizzaService.class);
        Pizza pizza = service.findById(68);
        assertEquals(new Pizza(68, "гавайская", List.of(new Ingredient(89, "ананас"),
                new Ingredient(86, "курица"),
                new Ingredient(92, "соус песто")), 1000), pizza);
        pizza = service.findById(100);
        assertNull(pizza);
    }

    @Test
    void updatePizzaAddRemoveIngredient() throws TransactionException, DaoException {
        PizzaService service = serviceFactory.getService(PizzaService.class);
        Pizza pizza = service.findById(68);
        pizza.setName("карибская");
        pizza.setPrice(800);
        service.addIngredientInPizza(pizza, new Ingredient(88, "лук"));
        service.update(pizza);
        assertEquals(68, pizza.getId());
        assertEquals(service.findById(68), pizza);
        assertEquals(new Pizza(68, "карибская", List.of(new Ingredient(89, "ананас"),
                new Ingredient(86, "курица"),
                new Ingredient(92, "соус песто"),
                new Ingredient(88, "лук")), 800), pizza);
        pizza.setName("гавайская");
        service.removeIngredientFromPizza(pizza, new Ingredient(88, "лук"));
        pizza.setPrice(1000);
        service.update(pizza);
        assertEquals(68, pizza.getId());
        assertEquals(service.findById(68), pizza);
        assertEquals(new Pizza(68, "гавайская", List.of(new Ingredient(89, "ананас"),
                new Ingredient(86, "курица"),
                new Ingredient(92, "соус песто")), 1000), pizza);
    }

    @Test
    void insertDelete() throws TransactionException, DaoException {
        PizzaService service = serviceFactory.getService(PizzaService.class);
        Pizza insertPizza = new Pizza(null, "деревенская", List.of(new Ingredient(86, "курица"),
                new Ingredient(85, "сыр")), 700);
        service.insert(insertPizza);
        assertNotEquals(null, insertPizza.getId());
        assertEquals(service.findById(insertPizza.getId()), insertPizza);
        service.deleteById(insertPizza.getId());
        assertNull(service.findById(insertPizza.getId()));

        insertPizza = new Pizza(null, "гавайская", List.of(new Ingredient(86, "курица"),
                new Ingredient(92, "соус песто")), 1200);
        service.insert(insertPizza);
        assertNull(insertPizza.getId());
    }
}