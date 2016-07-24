package ru.iav.takoe.statbm.json;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by takoe on 24.07.16.
 */
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
