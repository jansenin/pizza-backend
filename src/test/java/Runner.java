import by.fpmibsu.pizza_site.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import servlet.MainServlet;

import java.util.ArrayList;
import java.util.List;


public class Runner {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var pizza = new Pizza("Pizza name", new ArrayList<>(){{
            Ingredient i1 = new Ingredient("ingredient1");
            Ingredient i2 = new Ingredient("ingredient2");
            Ingredient i3 = new Ingredient("ingredient3");
            i1.setId(1);
            i2.setId(2);
            i3.setId(3);

            add(i1);
            add(i2);
            add(i3);

        }}, 100);
            pizza.setId(1);
            System.out.println("here1");
            System.out.println(new MainServlet.JSONDecoderImpl().getJSON(pizza));
    }
}
