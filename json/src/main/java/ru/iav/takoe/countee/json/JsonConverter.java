package ru.iav.takoe.countee.json;

import java.io.Serializable;

import com.google.gson.Gson;

public class JsonConverter {

    private Gson serializer;

    public JsonConverter() {
        serializer = GsonFactory.getGsonInstance();
    }

    public JsonConverter(Gson serializer) {
        this.serializer = serializer;
    }

    public String serialize(Serializable object) {
        return serializer.toJson(object);
    }

}
