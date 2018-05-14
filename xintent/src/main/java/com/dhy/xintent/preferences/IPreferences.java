package com.dhy.xintent.preferences;

import android.support.annotation.Nullable;

interface IPreferences {
    IPreferences set(Object value);

    /**
     * @param value use 'null' to remove it
     */
    <K extends Enum> IPreferences set(K key, Object value);

    <V> V get(Class<V> dataClass);

    <V> V get(Class<V> dataClass, @Nullable V defaultValue);

    <K extends Enum, V> V get(K key, Class<V> dataClass);

    <K extends Enum, V> V get(K key, Class<V> dataClass, @Nullable V defaultValue);

    /**
     * clear all Preferences for current Enum class. remove one item use {@link #set(Enum, Object)} 'null' please
     */
    IPreferences clear();

    IPreferences apply();

    void exit();
}
