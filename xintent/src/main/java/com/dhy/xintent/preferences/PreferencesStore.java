package com.dhy.xintent.preferences;

import android.support.annotation.Nullable;

public interface PreferencesStore {
    @Nullable
    <K extends Enum> String get(K key);

    <K extends Enum> PreferencesStore set(K key, @Nullable String value);

    PreferencesStore clear();

    PreferencesStore apply();

    void exit();
}
