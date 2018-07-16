package com.dhy.xintent.preferences;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class InnerPreferencesStore implements PreferencesStore {
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;

    InnerPreferencesStore(Context context, @NonNull String preferencesName) {
        preferences = context.getSharedPreferences(preferencesName, Activity.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public <K extends Enum> String get(K key) {
        return preferences.getString(key.name(), null);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public <K extends Enum> PreferencesStore set(K key, @Nullable String value) {
        if (edit == null) edit = preferences.edit();
        String keyName = key.name();
        if (value != null) {
            edit.putString(keyName, value);
        } else {
            edit.remove(keyName);
        }
        return this;
    }

    @Override
    public PreferencesStore clear() {
        preferences.edit().clear().apply();
        return this;
    }

    @Override
    public PreferencesStore apply() {
        if (edit != null) edit.apply();
        return this;
    }

    @Override
    public void exit() {
        preferences = null;
        edit = null;
    }
}
