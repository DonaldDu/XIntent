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

public class StaticPreferencesStore implements PreferencesStore {
    private JSONObject jsonObject;
    private final File jsonFile;

    StaticPreferencesStore(@NonNull Context context, @NonNull String preferencesName) {
        this(XCommon.getStaticDirectory(context), preferencesName);
    }

    StaticPreferencesStore(@NonNull File root, @NonNull String preferencesName) {
        jsonFile = getJsonPrefsFile(root, preferencesName);
        jsonObject = load(jsonFile);
    }

    private <K extends Enum> File getJsonPrefsFile(File root, @NonNull String preferencesName) {
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


    @Nullable
    @Override
    public <K extends Enum> String get(K key) {
        return (String) jsonObject.opt(key.name());

    }

    @Override
    public <K extends Enum> PreferencesStore set(K key, @Nullable String value) {
        try {
            String name = key.name();
            if (value == null) jsonObject.remove(name);
            else {
                jsonObject.put(name, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public PreferencesStore clear() {
        //noinspection ResultOfMethodCallIgnored
        jsonFile.delete();
        return this;
    }

    @Override
    public PreferencesStore apply() {
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
        return this;
    }

    @Override
    public void exit() {
        jsonObject = null;
    }
}
