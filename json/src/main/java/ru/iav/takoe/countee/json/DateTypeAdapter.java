package ru.iav.takoe.countee.json;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import static ru.iav.takoe.countee.logging.LogService.logError;

class DateTypeAdapter implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        String s = json.getAsString();

        DateFormat df = new SimpleDateFormat("MMM d, yyyy HH:mm:ss", Locale.UK);
        try {
            return df.parse(s);
        } catch (ParseException ex) {
            logError(ex.getMessage(), ex);
        }

        String dateFormatString = DateFormatUtil.determineDateFormat(s);
        if (dateFormatString == null) {
            throw new JsonParseException("Couldn't determine date format: " + s);
        }

        df = new SimpleDateFormat(dateFormatString, Locale.UK);
        try {
            return df.parse(s);
        } catch (ParseException ex) {
            logError(ex.getMessage(), ex);
            throw new JsonParseException(ex);
        }
    }
}
