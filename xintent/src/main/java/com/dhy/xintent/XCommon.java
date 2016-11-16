package com.dhy.xintent;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Field;

public class XCommon {
    //	region set text to textview

    /**
     * {@link #setTextWithFormat(Object, int, Object, int)}
     */
    public static TextView setTextWithFormat(Object container, int rid, Object value) {
        return setTextWithFormat(container, rid, value, true);
    }

    /**
     * {@link #setTextWithFormat(Object, int, Object, int)}
     */
    public static TextView setTextWithFormat(Object container, int rid, Object value, final boolean showOrGone) {
        return setTextWithFormat(findViewById(container, rid), value, showOrGone);
    }

    /**
     * @param container must be Activity, Dialog, Fragment, View or IFindViewById
     * @param value     {@link #setTextWithFormat(TextView, Object, int)}
     */
    public static TextView setTextWithFormat(Object container, int rid, Object value, int visibility) {
        return setTextWithFormat(findViewById(container, rid), value, visibility);
    }

    /**
     * {@link #setTextWithFormat(TextView, Object, int)}
     */
    public static TextView setTextWithFormat(TextView textView, Object value) {
        return setTextWithFormat(textView, value, true);
    }

    /**
     * {@link #setTextWithFormat(TextView, Object, int)}
     */
    public static TextView setTextWithFormat(TextView textView, Object value, final boolean showOrGone) {
        return setTextWithFormat(textView, value, showOrGone ? View.VISIBLE : View.GONE);
    }

    /**
     * use contentDescription as format, can't be null or empty
     *
     * @param value support Object[] for multiple values
     */
    public static TextView setTextWithFormat(TextView textView, Object value, final int visibility) {
        textView.setVisibility(visibility);
        if (visibility == View.GONE) {
            return textView;
        }
        CharSequence f = getFormat(textView);
        if (f != null) {
            String format = f.toString();
            if (value instanceof Number) {
                textView.setText(String.format(format, value));
            } else {//string
                if (value instanceof Object[]) {
                    textView.setText(String.format(format, (Object[]) value));
                } else {
                    textView.setText(String.format(format, (value != null) ? value : ""));
                }
            }
        } else {
            setText(textView, value);
        }
        return textView;
    }

    private static CharSequence getFormat(TextView textView) {
        return textView.getContentDescription();
    }

    public static TextView setText(Object container, int rid, Object value, Boolean showOrGone) {
        return setText(container, rid, value, showOrGone ? View.VISIBLE : View.GONE);
    }

    public static TextView setText(Object container, int rid, Object value, Integer visibility) {
        TextView textView = setText(container, rid, value);
        if (visibility != null) textView.setVisibility(visibility);
        return textView;
    }

    public static TextView setText(Object container, int rid, Object value) {
        return setText(findViewById(container, rid), value);
    }

    private static TextView findViewById(Object container, int rid) {
        TextView textView;
        if (container instanceof IFindViewById) {
            textView = (TextView) ((IFindViewById) container).findViewById(rid);
        } else if (container instanceof Activity) {
            textView = (TextView) ((Activity) container).findViewById(rid);
        } else if (container instanceof View) {
            textView = (TextView) ((View) container).findViewById(rid);
        } else if (container instanceof Dialog) {
            textView = (TextView) ((Dialog) container).findViewById(rid);
        } else if (container instanceof Fragment) {
            Fragment fragment = (Fragment) container;
            View view = fragment.getView();
            if (view != null) {
                textView = (TextView) view.findViewById(rid);
            } else textView = null;
        } else {
            throw new IllegalArgumentException("container must be Activity, Dialog, Fragment, View or IFindViewById");
        }
        return textView;
    }

    /**
     * set the value or empty for null
     */
    public static TextView setText(TextView textView, Object value) {
        textView.setText(value != null ? String.valueOf(value) : "");
        return textView;
    }
//	endregion

    //region Setting

    public static <K extends Enum & ISettingKey> String getSetting(Context context, K key) {
        return getSetting(context, getSharedPreferences(context), key, String.class, null);
    }

    public static <K extends Enum & ISettingKey, V> V getSetting(Context context, K key, Class<V> dataClass) {
        return getSetting(context, getSharedPreferences(context), key, dataClass, null);
    }

    public static <K extends Enum & ISettingKey, V> V getSetting(Context context, K key, Class<V> dataClass, @Nullable V defValue) {
        return getSetting(context, getSharedPreferences(context), key, dataClass, defValue);
    }

    private static <K extends Enum & ISettingKey, V> V getSetting(Context context, SharedPreferences sharedPreferences, K key, Class<V> dataClass, @Nullable V defValue) {
        String value = sharedPreferences.getString(key.name(), null);
        if (value == null) {
            return defValue;
        } else {
            try {
                return gson.fromJson(value, dataClass);
            } catch (Exception e) {
                if (isDebug(context)) {
                    throw e;
                }
                return null;
            }
        }
    }

    /**
     * @param key   view, id, name, activity
     * @param value string, int, boolean, float, double, long, data object, or null to remove setting
     */
    public static <K extends Enum & ISettingKey> void updateSetting(Context context, K key, Object value) {
        updateSetting(context, getSharedPreferences(context), key, value);
    }

    /**
     * @param value pass null to remove setting
     */
    private static <K extends Enum & ISettingKey> void updateSetting(Context context, SharedPreferences preferences, K key, @Nullable Object value) {
        SharedPreferences.Editor edit = preferences.edit();
        String keyName = key.name();
        if (value != null) {
            try {
                edit.putString(keyName, gson.toJson(value));
            } catch (Exception e) {
                if (isDebug(context)) {
                    throw e;
                }
            }
        } else {
            edit.remove(keyName);
        }
        edit.apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("settings", Activity.MODE_PRIVATE);
    }

    public static void clearSettings(final Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        preferences.edit().clear().apply();
    }
    //endregion

    //region init
    private static final Gson gson = new Gson();
    private static Boolean debug;

    /**
     * get application's BuildConfig.DEBUG
     */
    public static boolean isDebug(Context context) {
        if (debug != null) return debug;
        try {
            Class BuildConfig = Class.forName(context.getPackageName() + ".BuildConfig");
            Field debugFiled = BuildConfig.getField("DEBUG");
            debug = debugFiled.getBoolean(null);
        } catch (Exception e) {
            debug = false;
            e.printStackTrace();
        }
        return debug;
    }
//endregion
}
