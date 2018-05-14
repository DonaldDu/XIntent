package com.dhy.xintent.preferences;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class JsonPreferences implements IPreferences {
    static JsonConverter converter;

    public static void setJsonConverter(JsonConverter converter) {
        JsonPreferences.converter = converter;
    }

    public static <K extends Enum> void set(Context context, K key, Object value) {
        set(context, key, false, value);
    }

    public static <K extends Enum> void set(Context context, K key, boolean isStatic, Object value) {
        JsonPreferences preferences = new JsonPreferences(context, key, isStatic);
        preferences.set(key, value);
        preferences.apply();
        preferences.exit();
    }

    public static <K extends Enum, V> V get(Context context, K key, Class<V> dataClass) {
        return get(context, key, false, dataClass, null);
    }

    public static <K extends Enum, V> V get(Context context, K key, Class<V> dataClass, @Nullable V defaultValue) {
        return get(context, key, false, dataClass, defaultValue);
    }

    public static <K extends Enum, V> V get(Context context, K key, boolean isStatic, Class<V> dataClass) {
        return get(context, key, isStatic, dataClass, null);
    }

    public static <K extends Enum, V> V get(Context context, K key, boolean isStatic, Class<V> dataClass, @Nullable V defaultValue) {
        JsonPreferences preferences = new JsonPreferences(context, key, isStatic);
        V value = preferences.get(key, dataClass, defaultValue);
        preferences.exit();
        return value;
    }

    public static <K extends Enum> void clear(Context context, Class<K> cls) {
        clear(context, cls, false);
    }

    public static <K extends Enum> void clear(Context context, Class<K> cls, boolean isStatic) {
        JsonPreferences preferences = new JsonPreferences(context, cls, null, isStatic);
        preferences.clear();
        preferences.exit();
    }

    //region IPreferences
    static void initConverter() {
        if (converter == null) converter = new GsonConverter();
    }

    private final IPreferences preferences;

    public <K extends Enum> JsonPreferences(Context context, @NonNull K key, boolean isStatic) {
        this(context, (Class<K>) key.getClass(), key, isStatic);
    }

    public <K extends Enum> JsonPreferences(Context context, @NonNull Class<K> cls, @Nullable K key, boolean isStatic) {
        preferences = isStatic ? new StaticPreferences(context, cls, key) : new InnerPreferences(context, cls, key);
    }

    @Override
    public void set(Object value) {
        preferences.set(value);
    }

    @Override
    public <K extends Enum> void set(K key, Object value) {
        preferences.set(key, value);
    }

    @Override
    public <V> V get(Class<V> dataClass) {
        return preferences.get(dataClass);
    }

    @Override
    public <V> V get(Class<V> dataClass, @Nullable V defaultValue) {
        return preferences.get(dataClass, defaultValue);
    }

    @Override
    public <K extends Enum, V> V get(K key, Class<V> dataClass) {
        return preferences.get(key, dataClass);
    }

    @Override
    public <K extends Enum, V> V get(K key, Class<V> dataClass, @Nullable V defaultValue) {
        return preferences.get(key, dataClass, defaultValue);
    }

    @Override
    public void clear() {
        preferences.clear();
    }

    @Override
    public void apply() {
        preferences.apply();
    }

    @Override
    public void exit() {
        preferences.exit();
    }
//endregion
}
