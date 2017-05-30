package ru.iav.takoe.countee.json;

import com.google.gson.Gson;

import java.io.Serializable;

public class JsonConverter {

    private static JsonConverter instance;

    private Gson serializer;

    private JsonConverter() {
        serializer = GsonFactory.getGsonInstance();
    }

    public static JsonConverter getInstance() {
        if (instance == null) {
            instance = new JsonConverter();
        }
        return instance;
    }

    public String serialize(Serializable object) {
        return serializer.toJson(object);
    }

}
