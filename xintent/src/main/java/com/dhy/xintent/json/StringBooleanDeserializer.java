package com.dhy.xintent.json;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * true: [true, Y, 1], false: others
 */
public class StringBooleanDeserializer implements JsonDeserializer<Boolean> {
    public static void registerTypeAdapter(GsonBuilder builder) {
        StringBooleanDeserializer serializer = new StringBooleanDeserializer();
        builder.registerTypeAdapter(Boolean.class, serializer);
        builder.registerTypeAdapter(boolean.class, serializer);
    }

    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String s = json.getAsString();
        return "true".equals(s) || "Y".equalsIgnoreCase(s) || "1".equals(s);
    }
}
