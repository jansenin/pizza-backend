package servlet;

import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.utilites.JSONDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import by.fpmibsu.pizza_site.entity.Entity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(name = "MainServlet", value = "/api")
public class MainServlet extends HttpServlet {
    public static class JSONDecoderImpl implements JSONDecoder {
        private final ObjectMapper mapper;
        public JSONDecoderImpl() {
            mapper = new ObjectMapper();
        }
        @Override
        public <Type extends Entity> String getJSON(Type entity) throws JsonProcessingException {
            return mapper.writeValueAsString(entity);
        }
    }

    @Override
    public void init() throws ServletException {
        System.out.println("init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        System.out.println("here");
        var f = ObjectMapper.class;
        System.out.println("hehehehher");
// Get the printwriter object from response to write the required json object to the output stream
        PrintWriter out = resp.getWriter();
// Assuming your json object is **jsonObject**, perform the following, it will return your json object
        JSONDecoderImpl dec = new JSONDecoderImpl();
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
        System.out.println(dec.getJSON(pizza));
        String json = dec.getJSON(pizza);
        System.out.println("here2");
        out.print(json);
        out.flush();
    }
}
