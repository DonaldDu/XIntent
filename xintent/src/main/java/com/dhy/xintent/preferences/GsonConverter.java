package com.dhy.xintent.preferences;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

class GsonConverter implements ObjectConverter {
    private Gson gson = new Gson();

    @Override
    public String objectToString(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <V> V string2object(@NonNull String string, Class<V> dataClass) {
        return gson.fromJson(string, dataClass);
    }

    @Override
    public <V> List<V> string2listObject(@NonNull String string, Class<V> dataClass) {
        try {
            JSONArray array = new JSONArray(string);
            if (array.length() > 0) {
                List<V> list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    String json = array.get(i).toString();
                    list.add(gson.fromJson(json, dataClass));
                }
                return list;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
