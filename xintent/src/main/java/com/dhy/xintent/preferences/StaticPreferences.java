package com.dhy.xintent.preferences;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.dhy.xintent.XCommon;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static com.dhy.xintent.preferences.JsonPreferences.converter;

class StaticPreferences implements IPreferences {
    private Enum enumKey;
    private JSONObject jsonObject;
    private final File jsonFile;

    <K extends Enum> StaticPreferences(Context context, @NonNull Class<K> cls, @Nullable K key) {
        this(cls, key, XCommon.getStaticDirectory(context));
    }

    <K extends Enum> StaticPreferences(@NonNull Class<K> cls, @Nullable K key, File root) {
        JsonPreferences.initConverter();
        this.enumKey = key;
        this.jsonFile = getJsonPrefsFile(root, cls);
        jsonObject = load(jsonFile);
    }

    private <K extends Enum> File getJsonPrefsFile(File root, Class<K> cls) {
        String preferencesName = cls.getName();
        return new File(root, "json_prefs" + File.separator + preferencesName);
    }

    private void createNewFile(File file) {
        boolean ok = true;
        try {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                ok = file.getParentFile().mkdirs();
            }
        } catch (SecurityException e) {
            throw new IllegalStateException(e);
        }

        if (!ok) {
            throw new IllegalStateException("file mkdirs failed:" + file.getParentFile());
        }
        try {
            ok = file.createNewFile();
            if (!ok) {
                throw new IllegalStateException("createNewFile failed:" + file.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private JSONObject load(File file) {
        if (!file.exists()) createNewFile(file);
        JSONObject jsonObject;
        try {
            String json = FileUtils.readFileToString(file, Charset.defaultCharset());
            if (TextUtils.isEmpty(json)) {
                jsonObject = new JSONObject();
            } else jsonObject = new JSONObject(json);
        } catch (Exception e) {
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }

    @Override
    public void set(Object value) {
        set(enumKey, value);
    }

    @Override
    public <K extends Enum> void set(K key, Object value) {
        try {
            String name = key.name();
            if (value == null) jsonObject.remove(name);
            else {
                jsonObject.put(name, converter.objectToJson(value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
        Object v = jsonObject.opt(key.name());
        if (v != null) return converter.json2object(v.toString(), dataClass);
        return defaultValue;
    }

    @Override
    public void clear() {
        //noinspection ResultOfMethodCallIgnored
        jsonFile.delete();
    }

    @Override
    public void apply() {
        if (jsonObject.length() == 0) {
            //noinspection ResultOfMethodCallIgnored
            jsonFile.delete();
        } else {
            if (!jsonFile.exists()) createNewFile(jsonFile);
            try {
                FileUtils.writeStringToFile(jsonFile, jsonObject.toString(), Charset.defaultCharset());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public void exit() {
        jsonObject = null;
    }
}
