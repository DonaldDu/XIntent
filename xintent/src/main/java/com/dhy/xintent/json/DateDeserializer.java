package com.dhy.xintent.json;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer implements JsonDeserializer<Date> {
    public static void registerTypeAdapter(GsonBuilder builder) {
        builder.registerTypeAdapter(Date.class, new DateDeserializer());
    }

    private static final String FULL_STRING = "yyyy-MM-dd HH:mm:ss";
    private static final int FULL_SIZE = FULL_STRING.length();
    private static final SimpleDateFormat FULL = new SimpleDateFormat(FULL_STRING);

    private static final SimpleDateFormat MINUTE = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @NonNull
    @Override
    public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String s = json.getAsString();
        if (s != null && s.length() != 0) {
            if (s.contains("-")) {//date
                try {
                    return (s.length() == FULL_SIZE ? FULL : MINUTE).parse(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {//number
                try {
                    return new Date(Long.parseLong(s));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return new Date(0);
    }
}
