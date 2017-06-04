package ru.iav.takoe.countee.json;

import java.io.Serializable;

import com.google.gson.Gson;
import ru.iav.takoe.countee.json.exception.DeserializationException;

public class JsonParser {

    private Gson parser;

    public JsonParser() {
        parser = GsonFactory.getGsonInstance();
    }

    public JsonParser(Gson parser) {
        this.parser = parser;
    }

    /**
     * @param json a JSON string to be parsed
     * @param type type of the returned object
     * @return An object parsed from given JSON.
     * @throws DeserializationException if JSON parsing failed.
     */
    public <T extends Serializable> T deserialize(String json, Class<T> type) {
        try {
            return parser.fromJson(json, type);
        } catch (Exception e) {
            throw new DeserializationException(e.getMessage(), e);
        }
    }

}
