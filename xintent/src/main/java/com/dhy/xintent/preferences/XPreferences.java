package com.dhy.xintent.preferences;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class XPreferences extends BasePreferences {
    //region settings
    private static ObjectConverter converter;
    private static IFileNameGenerator generator;

    public static void setObjectConverter(ObjectConverter converter) {
        XPreferences.converter = converter;
    }

    public static void setFileNameGenerator(IFileNameGenerator generator) {
        XPreferences.generator = generator;
    }

    static {
        converter = new GsonConverter();
        generator = new SimpleFileNameGenerator();
    }

    //endregion

    //region methods
    public static <K extends Enum> void set(Context context, K key, Object value) {
        set(context, key, false, value);
    }

    public static <K extends Enum> void set(Context context, K key, boolean isStatic, Object value) {
        new XPreferences(context, key, isStatic).set(key, value).apply().exit();
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
        XPreferences preferences = new XPreferences(context, key, isStatic);
        V value = preferences.get(key, dataClass, defaultValue);
        preferences.exit();
        return value;
    }


    public static <K extends Enum> List<String> getList(Context context, K key) {
        return getList(context, key, false, String.class, null);
    }

    public static <K extends Enum, V> List<V> getList(Context context, K key, Class<V> dataClass) {
        return getList(context, key, false, dataClass, null);
    }

    public static <K extends Enum, V> List<V> getList(Context context, K key, Class<V> dataClass, @Nullable List<V> defaultValue) {
        return getList(context, key, false, dataClass, defaultValue);
    }

    public static <K extends Enum, V> List<V> getList(Context context, K key, boolean isStatic, Class<V> dataClass) {
        return getList(context, key, isStatic, dataClass, null);
    }

    public static <K extends Enum, V> List<V> getList(Context context, K key, boolean isStatic, Class<V> dataClass, @Nullable List<V> defaultValue) {
        XPreferences preferences = new XPreferences(context, key, isStatic);
        List<V> value = preferences.getList(key, dataClass, defaultValue);
        preferences.exit();
        return value;
    }

    public static <K extends Enum> void clear(Context context, Class<K> cls) {
        clear(context, cls, false);
    }

    public static <K extends Enum> void clear(Context context, Class<K> cls, boolean isStatic) {
        new XPreferences(context, cls, null, isStatic).clear().exit();
    }

    //endregion

    //region constructor

    public <K extends Enum> XPreferences(Context context, @NonNull K key) {
        this(context, key, false);
    }

    public <K extends Enum> XPreferences(Context context, @NonNull K key, boolean isStatic) {
        this(context, (Class<K>) key.getClass(), key, isStatic);
    }

    public <K extends Enum> XPreferences(Context context, @NonNull Class<K> cls, @Nullable K key, boolean isStatic) {
        super(key, getPreferencesStore(context, cls, isStatic), converter);
    }

    public <K extends Enum> XPreferences(@Nullable K key, @NonNull PreferencesStore store) {
        super(key, store, converter);
    }

    private static <K extends Enum> PreferencesStore getPreferencesStore(Context context, @NonNull Class<K> cls, boolean isStatic) {
        String name = generator.generate(cls);
        return isStatic ? new StaticPreferencesStore(context, name) : new InnerPreferencesStore(context, name);
    }
//endregion

}
