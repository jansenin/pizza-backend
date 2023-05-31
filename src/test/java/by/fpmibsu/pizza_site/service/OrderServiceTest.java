package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.TransactionFactory;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Order;
import by.fpmibsu.pizza_site.entity.OrderStatus;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    static private final ServiceFactoryImpl serviceFactory;

    static {
        try {
            serviceFactory = new ServiceFactoryImpl(new TransactionFactory());
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
        OrderService service = serviceFactory.getService(OrderService.class);
        List<Order> orders = service.findAll();
        assertEquals(1, orders.size());
    }
    @Test
    void findAllContainsTest() throws TransactionException, DaoException {
        OrderService service = serviceFactory.getService(OrderService.class);
        List<Order> orders = service.findAll();
        List<Pizza> pizzas =  new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>(List.of(
                new Ingredient("курица"), new Ingredient("ананас"), new Ingredient("соус песто")));
        ingredients.get(0).setId(86);
        ingredients.get(1).setId(89);
        ingredients.get(2).setId(92);
        Pizza pizza = new Pizza("гавайская", ingredients, 1000);
        pizza.setId(68);
        pizzas.add(pizza);
        ingredients = new ArrayList<>(List.of(
                new Ingredient("томатный соус"), new Ingredient("шампиньоны"), new Ingredient("охотничьи колбаски")));
        ingredients.get(0).setId(91);
        ingredients.get(1).setId(95);
        ingredients.get(2).setId(96);
        pizza = new Pizza("охотничья", ingredients, 800);
        pizza.setId(69);
        pizzas.add(pizza);
        Order order = new Order(pizzas, OrderStatus.COMPLETED, 1);
        order.setId(74);
        assertEquals(order, orders.get(0));
    }

    @Test
    void findByIdNotNullTest() throws TransactionException, DaoException {
        OrderService service = serviceFactory.getService(OrderService.class);
        Order order = service.findById(74);
        List<Pizza> pizzas =  new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>(List.of(
                new Ingredient("курица"), new Ingredient("ананас"), new Ingredient("соус песто")));
        ingredients.get(0).setId(86);
        ingredients.get(1).setId(89);
        ingredients.get(2).setId(92);
        Pizza pizza = new Pizza("гавайская", ingredients, 1000);
        pizza.setId(68);
        pizzas.add(pizza);
        ingredients = new ArrayList<>(List.of(
                new Ingredient("томатный соус"), new Ingredient("шампиньоны"), new Ingredient("охотничьи колбаски")));
        ingredients.get(0).setId(91);
        ingredients.get(1).setId(95);
        ingredients.get(2).setId(96);
        pizza = new Pizza("охотничья", ingredients, 800);
        pizza.setId(69);
        pizzas.add(pizza);
        Order checkOrder = new Order(pizzas, OrderStatus.COMPLETED, 1);
        checkOrder.setId(74);
        assertEquals(checkOrder, order);
    }

    @Test
    void findByIdNullTest() throws TransactionException, DaoException {
        OrderService service = serviceFactory.getService(OrderService.class);
        Order order = service.findById(34);
        assertNull(order);
    }

    @Test
    void allowedUpdateTest() throws TransactionException, DaoException {
        OrderService service = serviceFactory.getService(OrderService.class);
        Order order = service.findById(74);
        order.setOrderStatus(OrderStatus.REJECTED);
        order.setUserId(2);
        service.update(order);
        assertEquals(74, order.getId());
        assertEquals(OrderStatus.REJECTED, order.getOrderStatus());
        assertEquals(service.findById(74), order);
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setUserId(1);
        service.update(order);
        assertEquals(74, order.getId());
        assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        assertEquals(service.findById(74), order);
    }

    @Test
    void notAllowedUpdateTest() throws TransactionException, DaoException {
        OrderService service = serviceFactory.getService(OrderService.class);
        Order order = new Order(List.of(), OrderStatus.COMPLETED, 3);
        order.setId(29);
        service.update(order);
        assertNull(order.getId());
    }

    @Test
    void insertDelete() throws TransactionException, DaoException {
        OrderService service = serviceFactory.getService(OrderService.class);
        List<Ingredient> ingredients = new ArrayList<>(List.of(
                new Ingredient("томатный соус"), new Ingredient("шампиньоны"), new Ingredient("охотничьи колбаски")));
        ingredients.get(0).setId(91);
        ingredients.get(1).setId(95);
        ingredients.get(2).setId(96);
        Pizza pizza = new Pizza("охотничья", ingredients, 800);
        pizza.setId(69);
        Order insertOrder = new Order(List.of(pizza), OrderStatus.IN_PROCESS, 2);
        service.insert(insertOrder);
        assertNotEquals(null, insertOrder.getId());
        assertEquals(service.findById(insertOrder.getId()), insertOrder);
        service.deleteById(insertOrder.getId());
        assertNull(service.findById(insertOrder.getId()));
    }

    @Test
    void findAllUserOrders() throws TransactionException, DaoException {
        OrderService service = serviceFactory.getService(OrderService.class);
        List<Order> orders = service.findAllUserOrders(1);
        assertEquals(1, orders.size());
        assertEquals(service.findById(74), orders.get(0));

        orders = service.findAllUserOrders(40);
        assertTrue(orders.isEmpty());
    }
}