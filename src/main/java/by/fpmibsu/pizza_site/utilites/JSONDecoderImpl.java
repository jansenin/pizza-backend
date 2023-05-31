package by.fpmibsu.pizza_site.utilites;

import by.fpmibsu.pizza_site.entity.Entity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONDecoderImpl implements JSONDecoder {
    private final ObjectMapper mapper;
    public JSONDecoderImpl() {
        mapper = new ObjectMapper();
    }
    @Override
    public <Type extends Entity> String getJSON(Type entity) throws JsonProcessingException {
        return mapper.writeValueAsString(entity);
    }
}
