package by.fpmibsu.pizza_site.utilites;
import by.fpmibsu.pizza_site.entity.Entity;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface JSONDecoder {
    <Type extends Entity> String getJSON(Type entity) throws JsonProcessingException;
}
