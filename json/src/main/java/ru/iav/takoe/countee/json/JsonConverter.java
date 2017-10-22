package ru.iav.takoe.countee.json;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter {

    private Gson serializer;

    public JsonConverter() {
        serializer = new GsonBuilder()
                .setDateFormat("MMM dd, yyyy HH:mm:ss")
                .create();
    }

    public JsonConverter(Gson serializer) {
        this.serializer = serializer;
    }

    public String serialize(Serializable object) {
        return serializer.toJson(object);
    }

}
