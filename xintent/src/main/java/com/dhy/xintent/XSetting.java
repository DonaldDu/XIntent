package com.dhy.xintent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

public class XSetting<T extends XSetting> implements Serializable {
    transient Enum key;
    transient Class<T> cls;
    transient Context context;

    /**
     * get stored setting or new one if not found
     */
    @NonNull
    public static <T extends XSetting> T getInstanceOrNew(Context context, Enum key, Class<T> cls) {
        T setting = XCommon.getSetting(context, key, cls);
        if (setting == null) {
            try {
                setting = cls.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        setting.key = key;
        setting.cls = cls;
        setting.context = context;
        return setting;
    }

    /**
     * get stored setting or null if not found
     */
    @Nullable
    public static <T extends XSetting> T getInstance(Context context, Enum key, Class<T> cls) {
        T setting = XCommon.getSetting(context, key, cls);
        if (setting != null) {
            setting.key = key;
            setting.cls = cls;
            setting.context = context;
        }
        return setting;
    }

    /**
     * get stored setting or null if not found,
     * see also {@link #getInstance(Context, Enum, Class)}
     */
    @Nullable
    public T load() {
        return getInstance(context, key, cls);
    }

    /**
     * support only for object created by {@link #getInstance(Context, Enum, Class)} or {@link #getInstanceOrNew(Context, Enum, Class)}
     */
    public T save() {
        XCommon.updateSetting(context, key, this);
        return cls.cast(this);
    }
}
