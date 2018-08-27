package com.dhy.xintent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * An easy way to handle intent extra
 */
@SuppressLint("ParcelCreator")
public class XIntent extends Intent {
    public static final String KEY_EXTRA = XIntent.class.getName();

    public XIntent(Bundle bundle) {
        super();
        replaceExtras(bundle);
    }

    /**
     * maybe you need {@link #startActivity(Context, Class, Serializable...)}
     */
    public XIntent(Context context, Class<?> cls, Serializable... serializable) {
        super(context, cls);
        putSerializableExtra(this, serializable);
    }

    public static void startActivity(Context context, Class<?> cls, Serializable... serializable) {
        context.startActivity(new XIntent(context, cls, serializable));
    }

    public static Intent putSerializableExtra(Intent intent, Serializable... serializable) {
        if (serializable.length != 0) intent.putExtra(KEY_EXTRA, serializable);
        return intent;
    }

    /**
     * use {@link #readSerializableExtra(Activity, Class)} or other methods to get data out
     */
    public static void putSerializableExtra(Bundle bundle, Serializable... serializable) {
        if (serializable.length != 0) bundle.putSerializable(KEY_EXTRA, serializable);
    }

    public static void putSerializableExtra(Activity activity, Serializable... serializable) {
        putSerializableExtra(activity.getIntent(), serializable);
    }

    public static <T> T readSerializableExtra(Activity activity, Class<T> cls, T defaultValue) {
        return readSerializableExtra(activity.getIntent(), cls, defaultValue);
    }

    public static <T> T readSerializableExtra(Activity activity, Class<T> cls) {
        return readSerializableExtra(activity.getIntent(), cls);
    }

    public static Serializable readSerializableExtra(Activity activity) {
        return readSerializableExtra(activity.getIntent());
    }

    public static <T> T readSerializableExtra(Intent intent, Class<T> cls) {
        return readSerializableExtra(intent, cls, null);
    }

    public static Serializable readSerializableExtra(Intent intent) {
        return intent.getSerializableExtra(KEY_EXTRA);
    }

    public static Serializable readSerializableExtra(Activity activity, int index) {
        return readSerializableExtra(activity.getIntent(), index);
    }

    public static Serializable readSerializableExtra(Intent intent, int index) {
        return readSerializableExtra(intent, Serializable.class, index, null);
    }

    public static <T> T readSerializableExtra(Activity activity, @NonNull T defaultValue) {
        Class<T> cls = (Class<T>) defaultValue.getClass();
        return readSerializableExtra(activity, cls, defaultValue);
    }

    public static <T> T readSerializableExtra(Activity activity, Class<T> cls, int index, T defaultValue) {
        return readSerializableExtra(activity.getIntent(), cls, index, defaultValue);
    }

    public static <T> T readSerializableExtra(Intent intent, Class<T> cls, T defaultValue) {
        Serializable serializable = readSerializableExtra(intent);
        if (serializable instanceof Object[]) {
            Object[] data = (Object[]) serializable;
            for (Object d : data) {
                if (cls.isInstance(d)) {
                    return cls.cast(d);
                }
            }
        }
        return defaultValue;
    }

    @Nullable
    public static <T> List<T> readSerializableExtraList(Activity activity, Class<T> cls) {
        return readSerializableExtraList(activity.getIntent(), cls);
    }

    @Nullable
    public static <T> List<T> readSerializableExtraList(Intent intent, Class<T> cls) {
        Serializable serializable = readSerializableExtra(intent);
        if (serializable instanceof Object[]) {
            Object[] data = (Object[]) serializable;
            List empty = null;
            for (Object d : data) {
                if (d instanceof List) {
                    List list = (List) d;
                    if (list.isEmpty()) empty = list;
                    if (!list.isEmpty() && cls.isInstance(list.get(0))) {
                        return (List<T>) d;
                    }
                }
            }
            return empty;
        }
        return null;
    }

    public static <T> T readSerializableExtra(Intent intent, Class<T> cls, int index, T defaultValue) {
        Serializable serializable = readSerializableExtra(intent);
        if (serializable instanceof Object[]) {
            Object[] data = (Object[]) serializable;
            if (index < data.length) return cls.cast(data[index]);
        }
        return defaultValue;
    }
}
