package com.dhy.xintent.preferences;

import android.support.annotation.Nullable;

public interface IPreferences {
    /**
     * @param value use 'null' to remove it
     */
    <K extends Enum> IPreferences set(K key, Object value);

    IPreferences set(Object value);

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

    /**
     * clear all Preferences for current Enum class. remove one item use {@link #set(Enum, Object)} 'null' please
     */
    IPreferences clear();

    IPreferences apply();

    void exit();
}
