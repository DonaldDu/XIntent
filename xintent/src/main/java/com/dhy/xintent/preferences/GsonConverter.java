package com.dhy.xintent.preferences;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GsonConverter implements ObjectConverter {
    private final Gson gson;

    public GsonConverter() {
        this(null);
    }

    public GsonConverter(@Nullable Gson gson) {
        this.gson = gson != null ? gson : new Gson();
    }

    @Override
    public String objectToString(@NonNull Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <V> V string2object(@NonNull String string, Class<V> dataClass) {
        return gson.fromJson(string, dataClass);
    }

    @NonNull
    @Override
    public <V> List<V> string2listObject(@NonNull String string, Class<V> dataClass) {
        List<V> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(string);
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    String json = array.get(i).toString();
                    list.add(gson.fromJson(json, dataClass));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
