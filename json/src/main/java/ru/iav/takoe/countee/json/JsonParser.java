package ru.iav.takoe.countee.json;

import com.google.gson.Gson;
import ru.iav.takoe.countee.json.exception.DeserializationException;

import java.io.Serializable;

public class JsonParser {

    private static JsonParser instance;

    private Gson parser;

    private JsonParser() {
        parser = GsonFactory.getGsonInstance();
    }

    public static JsonParser getInstance() {
        if (instance == null) {
            instance = new JsonParser();
        }
        return instance;
    }

    public <T extends Serializable> T deserialize(String json, Class<T> type) throws DeserializationException {
        try {
            return parser.fromJson(json, type);
        } catch (Exception e) {
            throw new DeserializationException(e.getMessage(), e);
        }
    }

}
