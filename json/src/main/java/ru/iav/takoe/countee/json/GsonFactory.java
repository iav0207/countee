package ru.iav.takoe.countee.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class GsonFactory {

    static Gson getGsonInstance() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

}
