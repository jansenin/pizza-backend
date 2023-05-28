package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.TransactionFactory;
import by.fpmibsu.pizza_site.entity.*;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    static private final ServiceFactory serviceFactory;

    static {
        try {
            serviceFactory = new ServiceFactory(new TransactionFactory());
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findAll() throws TransactionException, DaoException {
        OrderServiceInterface service = serviceFactory.getService(OrderServiceInterface.class);
        List<Order> orders = service.findAll();
        assertEquals(1, orders.size());
        assertEquals(new Order(74, List.of(new Pizza(68, "гавайская", List.of(
                new Ingredient(86, "курица"), new Ingredient(89, "ананас"), new Ingredient(92, "соус песто")
        ), 1000), new Pizza(69, "охотничья", List.of(
                new Ingredient(91, "томатный соус"), new Ingredient(95, "шампиньоны"), new Ingredient(96, "охотничьи колбаски")
        ), 800)), OrderStatus.COMPLETED, 1), orders.get(0));
    }

    @Test
    void findById() throws TransactionException, DaoException {
        OrderServiceInterface service = serviceFactory.getService(OrderServiceInterface.class);
        Order order = service.findById(74);
        assertEquals(new Order(74, List.of(new Pizza(68, "гавайская", List.of(
                new Ingredient(86, "курица"), new Ingredient(89, "ананас"), new Ingredient(92, "соус песто")
        ), 1000), new Pizza(69, "охотничья", List.of(
                new Ingredient(91, "томатный соус"), new Ingredient(95, "шампиньоны"), new Ingredient(96, "охотничьи колбаски")
        ), 800)), OrderStatus.COMPLETED, 1), order);
        order = service.findById(34);
        assertNull(order);
    }

    @Test
    void update() throws TransactionException, DaoException {
        OrderServiceInterface service = serviceFactory.getService(OrderServiceInterface.class);
        Order order = service.findById(74);
        order.setOrderStatus(OrderStatus.REJECTED);
        order.setUserId(2);
        service.update(order);
        assertEquals(74, order.getId());
        assertEquals(service.findById(74), order);
        assertEquals(new Order(74, List.of(new Pizza(68, "гавайская", List.of(
                new Ingredient(86, "курица"), new Ingredient(89, "ананас"), new Ingredient(92, "соус песто")
        ), 1000), new Pizza(69, "охотничья", List.of(
                new Ingredient(91, "томатный соус"), new Ingredient(95, "шампиньоны"), new Ingredient(96, "охотничьи колбаски")
        ), 800)), OrderStatus.REJECTED, 2), order);
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setUserId(1);
        service.update(order);
        assertEquals(74, order.getId());
        assertEquals(service.findById(74), order);
        assertEquals(new Order(74, List.of(new Pizza(68, "гавайская", List.of(
                new Ingredient(86, "курица"), new Ingredient(89, "ананас"), new Ingredient(92, "соус песто")
        ), 1000), new Pizza(69, "охотничья", List.of(
                new Ingredient(91, "томатный соус"), new Ingredient(95, "шампиньоны"), new Ingredient(96, "охотничьи колбаски")
        ), 800)), OrderStatus.COMPLETED, 1), order);

        order = new Order(29, List.of(), OrderStatus.COMPLETED, 3);
        service.update(order);
        assertEquals(Order.ID_NOT_DEFINED, order.getId());
    }

    @Test
    void insertDelete() throws TransactionException, DaoException {
        OrderServiceInterface service = serviceFactory.getService(OrderServiceInterface.class);
        Order insertOrder = new Order(Order.ID_NOT_DEFINED, List.of(new Pizza(69, "охотничья", List.of(
                new Ingredient(91, "томатный соус"), new Ingredient(95, "шампиньоны"),
                new Ingredient(96, "охотничьи колбаски")), 800)), OrderStatus.IN_PROCESS, 2);
        service.insert(insertOrder);
        assertNotEquals(Order.ID_NOT_DEFINED, insertOrder.getId());
        assertEquals(service.findById(insertOrder.getId()), insertOrder);
        service.deleteById(insertOrder.getId());
        assertNull(service.findById(insertOrder.getId()));
    }

    @Test
    void findAllUserOrders() throws TransactionException, DaoException {
        OrderServiceInterface service = serviceFactory.getService(OrderServiceInterface.class);
        User user = new User(UserRole.ADMIN, "dzen", 1, "secret");
        List<Order> orders = service.findAllUserOrders(user);
        assertEquals(1, orders.size());
        assertEquals(service.findById(74), orders.get(0));

        user = new User(UserRole.CLIENT, "anon", 24, "snthd");
        orders = service.findAllUserOrders(user);
        assertTrue(orders.isEmpty());
    }
}