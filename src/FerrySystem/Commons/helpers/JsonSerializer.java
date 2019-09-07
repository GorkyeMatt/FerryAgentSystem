package FerrySystem.Commons.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer {

    private ObjectMapper objectMapper = new ObjectMapper();

    public String serialize(Object value){
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
