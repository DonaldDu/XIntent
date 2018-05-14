package com.dhy.xintent.preferences;

import com.google.gson.Gson;

final public class GsonConverter implements JsonConverter {
    private Gson gson = new Gson();

    @Override
    public String objectToJson(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <V> V json2object(String json, Class<V> dataClass) {
        return gson.fromJson(json, dataClass);
    }
}
