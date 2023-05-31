import by.fpmibsu.pizza_site.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


public class Runner {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(UserRole.CLIENT, "abacaba", "kek");
        user.setId(7);
        String json = mapper.writeValueAsString(user);
        System.out.println(json);
        List<Ingredient> ingredients = new ArrayList<>(List.of(new Ingredient("ананас"),
                new Ingredient("курица"),
                new Ingredient("соус песто")));
        ingredients.get(0).setId(89);
        ingredients.get(1).setId(86);
        ingredients.get(2).setId(92);
        Pizza pizza = new Pizza("гавайская", ingredients, 1000);
        pizza.setId(68);
        json = mapper.writeValueAsString(pizza);
        System.out.println(json);
        List<Pizza> pizzas =  new ArrayList<>();
        ingredients = new ArrayList<>(List.of(
                new Ingredient("курица"), new Ingredient("ананас"), new Ingredient("соус песто")));
        ingredients.get(0).setId(86);
        ingredients.get(1).setId(89);
        ingredients.get(2).setId(92);
        pizza = new Pizza("гавайская", ingredients, 1000);
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
        json = mapper.writeValueAsString(order);
        System.out.println(json);
    }
}
