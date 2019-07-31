package FerrySystem.Commons.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonSerializer {

    private ObjectMapper objectMapper = new ObjectMapper();

    public String serialize(Object value){
        try {
            String serialized = objectMapper.writeValueAsString(value);
            return serialized;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
