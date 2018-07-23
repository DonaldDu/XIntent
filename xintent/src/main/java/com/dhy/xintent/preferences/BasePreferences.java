package com.dhy.xintent.preferences;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

class BasePreferences implements IPreferences {
    private final Enum enumKey;
    private final PreferencesStore store;
    private final ObjectConverter converter;

    public <K extends Enum> BasePreferences(@Nullable K key, @NonNull PreferencesStore store, @NonNull ObjectConverter converter) {
        this.enumKey = key;
        this.store = store;
        this.converter = converter;
    }

    @Override
    public IPreferences set(@Nullable Object value) {
        return set(enumKey, value);
    }

    @Override
    public <K extends Enum> IPreferences set(K key, @Nullable Object value) {
        store.set(key, value != null ? converter.objectToString(value) : null);
        return this;
    }

    @Override
    public <V> V get(Class<V> dataClass) {
        return get(enumKey, dataClass, null);
    }

    @Override
    public String get() {
        return get(enumKey, String.class, null);
    }

    @Override
    public <K extends Enum, V> List<V> getList(K key, Class<V> dataClass, @Nullable List<V> defaultValue) {
        String v = store.get(key);
        if (v == null) return defaultValue;
        return converter.string2listObject(v, dataClass);
    }

    @Override
    public <K extends Enum, V> List<V> getList(K key, Class<V> dataClass) {
        return getList(key, dataClass, null);
    }

    @Override
    public <K extends Enum> List<String> getList(K key) {
        return getList(key, String.class, null);
    }

    @Override
    public <V> List<V> getList(Class<V> dataClass, @Nullable List<V> defaultValue) {
        return getList(enumKey, dataClass, defaultValue);
    }

    @Override
    public <V> List<V> getList(Class<V> dataClass) {
        return getList(enumKey, dataClass, null);
    }

    @Override
    public List<String> getList() {
        return getList(enumKey, String.class, null);
    }

    @Override
    public <V> V get(Class<V> dataClass, @Nullable V defaultValue) {
        return get(enumKey, dataClass, defaultValue);
    }

    @Override
    public <K extends Enum> String get(K key) {
        return get(key, String.class, null);
    }

    @Override
    public <K extends Enum, V> V get(K key, Class<V> dataClass) {
        return get(key, dataClass, null);
    }

    @Override
    public <K extends Enum, V> V get(K key, Class<V> dataClass, @Nullable V defaultValue) {
        String v = store.get(key);
        if (v != null) return converter.string2object(v, dataClass);
        return defaultValue;
    }

    @Override
    public IPreferences clear() {
        store.clear();
        return this;
    }

    @Override
    public IPreferences apply() {
        store.apply();
        return this;
    }

    @Override
    public void exit() {
        store.exit();
    }
}
