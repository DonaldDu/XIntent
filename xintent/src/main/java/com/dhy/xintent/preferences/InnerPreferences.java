package com.dhy.xintent.preferences;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dhy.xintent.XCommon;

import static com.dhy.xintent.preferences.JsonPreferences.converter;

class InnerPreferences implements IPreferences {
    private Enum enumKey;
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;

    <K extends Enum> InnerPreferences(Context context, @NonNull Class<K> cls, @Nullable K key) {
        JsonPreferences.initConverter();
        this.context = context;
        this.enumKey = key;
        preferences = context.getSharedPreferences(cls.getName(), Activity.MODE_PRIVATE);
    }

    <K extends Enum> InnerPreferences(Context context, K key) {
        this(context, (Class<K>) key.getClass(), key);
    }

    @Override
    public void set(Object value) {
        set(enumKey, value);
    }


    @SuppressLint("CommitPrefEdits")
    @Override
    public <K extends Enum> void set(K key, Object value) {
        if (edit == null) edit = preferences.edit();
        String keyName = key.name();
        if (value != null) {
            try {
                edit.putString(keyName, converter.objectToJson(value));
            } catch (Exception e) {
                if (XCommon.isDebug(context)) {
                    throw e;
                }
            }
        } else {
            edit.remove(keyName);
        }
    }

    @Override
    public <V> V get(Class<V> dataClass) {
        return get(enumKey, dataClass, null);
    }

    @Override
    public <V> V get(Class<V> dataClass, @Nullable V defaultValue) {
        return get(enumKey, dataClass, defaultValue);
    }

    @Override
    public <K extends Enum, V> V get(K key, Class<V> dataClass) {
        return get(key, dataClass, null);
    }

    @Override
    public <K extends Enum, V> V get(K key, Class<V> dataClass, @Nullable V defaultValue) {
        String value = preferences.getString(key.name(), null);
        if (value == null) {
            return defaultValue;
        } else {
            try {
                return converter.json2object(value, dataClass);
            } catch (Exception e) {
                if (XCommon.isDebug(context)) {
                    throw e;
                }
                return defaultValue;
            }
        }
    }

    @Override
    public void clear() {
        preferences.edit().clear().apply();
    }

    @Override
    public void apply() {
        if (edit != null) edit.apply();
    }

    @Override
    public void exit() {
        preferences = null;
        context = null;
        edit = null;
    }
}
