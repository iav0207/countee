package ru.iav.takoe.countee.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by takoe on 24.07.16.
 */
class GsonFactory {

    static Gson getGsonInstance() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

}
