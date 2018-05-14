package com.dhy.xintent.preferences;

import android.support.annotation.Nullable;

interface IPreferences {
    void set(Object value);

    /**
     * @param value use 'null' to remove it
     */
    <K extends Enum> void set(K key, Object value);

    <V> V get(Class<V> dataClass);

    <V> V get(Class<V> dataClass, @Nullable V defaultValue);

    <K extends Enum, V> V get(K key, Class<V> dataClass);

    <K extends Enum, V> V get(K key, Class<V> dataClass, @Nullable V defaultValue);

    /**
     * clear all Preferences for current Enum class. remove one item use {@link #set(Enum, Object)} 'null' please
     */
    void clear();

    void apply();

    void exit();
}
