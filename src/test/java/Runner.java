import by.fpmibsu.pizza_site.dao.*;
import by.fpmibsu.pizza_site.dao.ConnectionCreator;
import by.fpmibsu.pizza_site.entity.*;
import by.fpmibsu.pizza_site.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Runner {
    public static void main(String[] args) throws SQLException, DaoException {
        Connection userConnection = ConnectionCreator.createConnection();
        UserDaoImpl userDao = new UserDaoImpl(userConnection);
        System.out.println("вывод всех пользователей:");
        List<User> users = userDao.findAll();
        for (var i : users) {
            System.out.println(i.toString());
        }
        System.out.println("\nвывод пользователя с первым id:");
        User user = userDao.findById(1);
        System.out.println(user.toString());
        System.out.println("\nвывод пользователя с логином parfen:");
        user = userDao.findUserByLogin("parfen");
        System.out.println(user.toString());
        System.out.println("\nвывод несуществующего пользователя:");
        user = userDao.findUserByLogin("ttt");
        System.out.println("пользователь не найден = " + (user == null));
        System.out.println("\nдобавление двух новых пользователей:");
        userDao.insert(new User(UserRole.CLIENT, "guy1", "abacaba"));
        userDao.insert(user = new User(UserRole.CLIENT, "guy2","dddd"));
        users = userDao.findAll();
        for (var i : users) {
            System.out.println(i.toString());
        }
        System.out.println("\nявляется ли  abacaba паролем для guy1 = " + userDao.checkUserPassword(userDao.findUserByLogin("guy1"), "abacaba"));
        System.out.println("является ли abacaba паролем для guy2 = " + userDao.checkUserPassword(userDao.findUserByLogin("guy2"), "abacaba"));
        System.out.println("\nредактируем guy2:");
        user.setLogin("BIGGUY");
        user.setRole(UserRole.STAFF);
        userDao.update(user);
        user.setPassword("aaa");
        System.out.println("является ли aaa паролем для BIGGUY до апдейта пароля = " + userDao.checkUserPassword(user, "aaa"));
        userDao.update(user);
        System.out.println("является ли aaa паролем для BIGGUY после апдейта пароля = " + userDao.checkUserPassword(user, "aaa"));
        users = userDao.findAll();
        for (var i : users) {
            System.out.println(i.toString());
        }
        System.out.println("\nудаление гаев:");
        userDao.deleteByLogin("guy1");
        userDao.deleteByLogin("BIGGUY");
        users = userDao.findAll();
        for (var i : users) {
            System.out.println(i.toString());
        }
        // users tested
        System.out.println("\nвывод ингредиента с первым id:");
        Connection ingredientConnection = ConnectionCreator.createConnection();
        IngredientDaoImpl ingredientDao = new IngredientDaoImpl(ingredientConnection);
        Ingredient ingredient = ingredientDao.findById(1);
        System.out.println(ingredient == null ? "no such ingredient" : ingredient.toString());
        System.out.println("\nвывод всех ингредиентов:");
        List<Ingredient> ingredients = ingredientDao.findAll();
        for (var i : ingredients) {
            System.out.println(i.toString());
        }
        System.out.println("\nдобавление трюфеля и кокоса");
        ingredient = new Ingredient("кокос");
        ingredientDao.insert(ingredient);
        ingredient = new Ingredient("трюфель");
        ingredientDao.insert(ingredient);
        ingredients = ingredientDao.findAll();
        for (var i : ingredients) {
            System.out.println(i.toString());
        }
        ingredient.setName("ТРЮФЕЛЬ");
        ingredientDao.update(ingredient);
        System.out.println("\nизменение названия трюфеля");
        ingredients = ingredientDao.findAll();
        for (var i : ingredients) {
            System.out.println(i.toString());
        }
        System.out.println("\nвывод кокоса по имени:");
        ingredient = ingredientDao.findIngredientByName("кокос");
        System.out.println(ingredient.toString());
        System.out.println("\nудаление кокоса через энтити и ТРЮФЕЛЯ через название:");
        ingredientDao.deleteByIngredient(ingredient);
        ingredientDao.deleteByName("ТРЮФЕЛЬ");
        ingredients = ingredientDao.findAll();
        for (var i : ingredients) {
            System.out.println(i.toString());
        }
        // ingredients tested
        System.out.println("\nвывод всех пицц:");
        Connection pizzaConnection = ConnectionCreator.createConnection();
        PizzaDaoImpl pizzaDao = new PizzaDaoImpl(pizzaConnection);
        List<Pizza> pizzas = pizzaDao.findAll();
        for (var i : pizzas) {
            System.out.println(i.toString());
        }
        System.out.println("\nдобавление мясной и домашней пиццы");
        Pizza pizza = new Pizza("мясная", new ArrayList<>(List.of(ingredientDao.findIngredientByName("бекон"), ingredientDao.findIngredientByName("курица"))), 1300);
        pizzaDao.insert(pizza);
        pizza = new Pizza("домашняя", new ArrayList<>(List.of(ingredientDao.findIngredientByName("сыр"), ingredientDao.findIngredientByName("помидор"))), 900);
        pizzaDao.insert(pizza);
        pizzas = pizzaDao.findAll();
        for (var i : pizzas) {
            System.out.println(i.toString());
        }
        Connection orderConnection = ConnectionCreator.createConnection();
        OrderDaoImpl orderDao = new OrderDaoImpl(orderConnection);
        System.out.println("\nвывод всех заказов:");
        List<Order> orders = orderDao.findAll();
        for (var i : orders) {
            System.out.println(i.toString());
        }
        System.out.println("\nдобавление двух заказов:");
        orderDao.insert(new Order(new ArrayList<>(List.of(pizzaDao.findPizzaByName("гавайская"), pizzaDao.findPizzaByName("мясная"))), OrderStatus.IN_PROCESS, 1));
        orderDao.insert(new Order(new ArrayList<>(List.of(pizzaDao.findPizzaByName("домашняя"))), OrderStatus.COMPLETED, 2));
        orderDao.insert(new Order(new ArrayList<>(List.of(pizzaDao.findPizzaByName("мясная"))), OrderStatus.IN_PROCESS, 2));
        orders = orderDao.findAll();
        for (var i : orders) {
            System.out.println(i.toString());
        }
        System.out.println("\nпоиск заказов пользователя со вторым id");
        orders = orderDao.findAllUserOrders(userDao.findById(2));
        for (var i : orders) {
            System.out.println(i.toString());
        }
        System.out.println("\nизменение статуса заказа:");
        orders.get(1).setOrderStatus(OrderStatus.REJECTED);
        orderDao.update(orders.get(1));
        orders = orderDao.findAll();
        for (var i : orders) {
            System.out.println(i.toString());
        }
        System.out.println("удаление добавленных заказов по id");
        orders = orderDao.findAllUserOrders(userDao.findById(1));
        for (var i : orders) {
            orderDao.deleteById(i.getId());
        }
        orders = orderDao.findAllUserOrders(userDao.findById(2));
        for (var i : orders) {
            orderDao.deleteById(i.getId());
        }
        orders = orderDao.findAll();
        for (var i : orders) {
            System.out.println(i.toString());
        }
        // orders tested
        System.out.println("\nудаление мясной пиццы по названию, изменение имени домашней и добавление в неё ананаса");
        pizzaDao.deleteByName("мясная");
        pizza.setName("деревенская");
        pizzaDao.update(pizza);
        pizzaDao.addIngredientInPizza(pizza, ingredientDao.findIngredientByName("ананас"));
        pizzas = pizzaDao.findAll();
        for (var i : pizzas) {
            System.out.println(i.toString());
        }
        System.out.println("\nудаление деревенской пиццы по id");
        pizzaDao.deleteById(pizza.getId());
        pizzas = pizzaDao.findAll();
        for (var i : pizzas) {
            System.out.println(i.toString());
        }
        // pizzas tested
        orderConnection.close();
        userConnection.close();
        ingredientConnection.close();
        pizzaConnection.close();
    }
}
