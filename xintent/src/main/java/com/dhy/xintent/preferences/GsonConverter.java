package com.dhy.xintent.preferences;

import com.google.gson.Gson;

class GsonConverter implements ObjectConverter {
    private Gson gson = new Gson();

    @Override
    public String objectToString(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <V> V string2object(String string, Class<V> dataClass) {
        return gson.fromJson(string, dataClass);
    }
}
