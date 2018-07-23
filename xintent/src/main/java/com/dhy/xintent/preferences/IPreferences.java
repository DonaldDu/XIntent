package com.dhy.xintent.preferences;

import android.support.annotation.Nullable;

import java.util.List;

public interface IPreferences {
    /**
     * @param value use 'null' to remove it
     */
    <K extends Enum> IPreferences set(K key, @Nullable Object value);

    IPreferences set(@Nullable Object value);

    //region get setting with key
    <K extends Enum, V> V get(K key, Class<V> dataClass, @Nullable V defaultValue);

    <K extends Enum, V> V get(K key, Class<V> dataClass);

    /**
     * @return get string with key by default
     */
    <K extends Enum> String get(K key);
    //endregion

    //region get setting without key
    <V> V get(Class<V> dataClass, @Nullable V defaultValue);

    <V> V get(Class<V> dataClass);

    /**
     * @return get string with key by default
     */
    String get();

    //endregion

    //region getList
    //region get setting with key
    <K extends Enum, V> List<V> getList(K key, Class<V> dataClass, @Nullable List<V> defaultValue);

    <K extends Enum, V> List<V> getList(K key, Class<V> dataClass);

    /**
     * @return get string with key by default
     */
    <K extends Enum> List<String> getList(K key);
    //endregion

    //region get setting without key
    <V> List<V> getList(Class<V> dataClass, @Nullable List<V> defaultValue);

    <V> List<V> getList(Class<V> dataClass);

    /**
     * @return get string with key by default
     */
    List<String> getList();
//endregion
//endregion

    /**
     * clear all Preferences for current Enum class. remove one item use {@link #set(Enum, Object)} 'null' please
     */
    IPreferences clear();

    IPreferences apply();

    void exit();
}
