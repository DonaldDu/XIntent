package com.dhy.xintent;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhy.xintent.annotation.GenCode;
import com.dhy.xintent.annotation.Visibility;
import com.dhy.xintent.preferences.XPreferences;
import com.google.gson.Gson;

import java.io.File;
import java.lang.reflect.Field;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

class XCommonBase {
    //region setTextWithFormat
    @GenCode
    public static TextView setTextWithFormat(Activity container, @IdRes int rid, Object value, final Boolean show) {
        return setTextWithFormat((TextView) container.findViewById(rid), value, show);
    }

    @GenCode
    public static TextView setTextWithFormat(Activity container, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setTextWithFormat((TextView) container.findViewById(rid), value, visibility);
    }

    @GenCode
    public static TextView setTextWithFormat(Activity container, @IdRes int rid, Object value) {
        return setTextWithFormat((TextView) container.findViewById(rid), value);
    }

    public static TextView setTextWithFormat(TextView textView, Object value, @Nullable final Boolean show) {
        return setText(textView, getFormatedString(textView, value), show);
    }

    public static TextView setTextWithFormat(TextView textView, Object value, @Visibility @Nullable final Integer visibility) {
        return setText(textView, getFormatedString(textView, value), visibility);
    }

    /**
     * use contentDescription as format, if null or empty will be ignored
     *
     * @param value support Object[] for multiple values
     */
    public static TextView setTextWithFormat(TextView textView, Object value) {
        return setText(textView, getFormatedString(textView, value));
    }

    static Object getFormatedString(TextView textView, Object value) {
        CharSequence description = textView.getContentDescription();
        if (description != null) {
            String format = description.toString();
            if (value instanceof Number) {
                return String.format(format, value);
            } else {
                if (value instanceof Object[]) {
                    return String.format(format, (Object[]) value);
                } else {
                    return String.format(format, (value != null) ? value : "");
                }
            }
        } else return value;
    }

    //endregion
    //region setText

    @GenCode
    public static TextView setText(Activity container, @IdRes int rid, Object value, @Nullable Boolean show) {
        return setText((TextView) container.findViewById(rid), value, show);
    }

    @GenCode
    public static TextView setText(Activity container, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setText((TextView) container.findViewById(rid), value, visibility);
    }

    @GenCode
    public static TextView setText(Activity container, @IdRes int rid, Object value) {
        return setText((TextView) container.findViewById(rid), value);
    }

    public static TextView setText(TextView textView, Object value, final Boolean show) {
        return setText(textView, value, show == null ? null : (show ? VISIBLE : GONE));
    }

    public static TextView setText(TextView textView, Object value, @Visibility final Integer visibility) {
        setText(textView, value);
        if (visibility != null) textView.setVisibility(visibility);
        return textView;
    }

    public static TextView setText(TextView textView, Object value) {
        textView.setText(value != null ? String.valueOf(value) : "");
        return textView;
    }

    //endregion
    //region setImage
    @GenCode
    public static ImageView setImage(Activity container, @IdRes int rid, @DrawableRes int image, @Nullable final Boolean show) {
        return setImage((ImageView) container.findViewById(rid), image, show);
    }

    @GenCode
    public static ImageView setImage(Activity container, @IdRes int rid, @DrawableRes int image, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) container.findViewById(rid), image, visibility);
    }

    @GenCode
    public static ImageView setImage(Activity container, @IdRes int rid, @DrawableRes int image) {
        return setImage((ImageView) container.findViewById(rid), image);
    }

    @GenCode
    public static ImageView setImage(Activity container, @IdRes int rid, Uri uri, @Nullable final Boolean show) {
        return setImage((ImageView) container.findViewById(rid), uri, show);
    }

    @GenCode
    public static ImageView setImage(Activity container, @IdRes int rid, Uri uri, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) container.findViewById(rid), uri, visibility);
    }

    @GenCode
    public static ImageView setImage(Activity container, @IdRes int rid, Uri uri) {
        return setImage((ImageView) container.findViewById(rid), uri);
    }

    static ImageView setImage(ImageView imageView, @DrawableRes int image, @Nullable final Boolean show) {
        return setImage(imageView, image, show == null ? null : (show ? VISIBLE : GONE));
    }

    static ImageView setImage(ImageView imageView, @DrawableRes int image, @Visibility @Nullable final Integer visibility) {
        if (visibility != null) imageView.setVisibility(visibility);
        return setImage(imageView, image);
    }

    static ImageView setImage(ImageView imageView, @DrawableRes int image) {
        imageView.setImageResource(image);
        return imageView;
    }

    static ImageView setImage(ImageView imageView, Uri uri, @Nullable final Boolean show) {
        return setImage(imageView, uri, show == null ? null : (show ? VISIBLE : GONE));
    }

    static ImageView setImage(ImageView imageView, Uri uri, @Visibility @Nullable final Integer visibility) {
        if (visibility != null) imageView.setVisibility(visibility);
        return setImage(imageView, uri);
    }

    static ImageView setImage(ImageView imageView, Uri uri) {
        imageView.setImageURI(uri);
        return imageView;
    }

    //endregion
    //region Setting

    /**
     * use {@link XPreferences} please
     */
    @Deprecated
    @Nullable
    public static <K extends Enum> String getSetting(Context context, K key) {
        return getSetting(context, getSharedPreferences(context, key), key, String.class, null);
    }

    /**
     * use {@link XPreferences} please
     */
    @Deprecated
    @Nullable
    public static <K extends Enum, V> V getSetting(Context context, K key, Class<V> dataClass) {
        return getSetting(context, getSharedPreferences(context, key), key, dataClass, null);
    }

    /**
     * use {@link XPreferences} please
     */
    @Deprecated
    public static <K extends Enum, V> V getSetting(Context context, K key, Class<V> dataClass, @Nullable V defValue) {
        return getSetting(context, getSharedPreferences(context, key), key, dataClass, defValue);
    }

    /**
     * use {@link XPreferences} please
     */
    @Deprecated
    private static <K extends Enum, V> V getSetting(Context context, SharedPreferences sharedPreferences, K key, Class<V> dataClass, @Nullable V defValue) {
        String value = sharedPreferences.getString(key.name(), null);
        if (value == null) {
            return defValue;
        } else {
            if (gson == null) gson = new Gson();
            try {
                return gson.fromJson(value, dataClass);
            } catch (Exception e) {
                if (isDebug(context)) {
                    throw e;
                }
                return defValue;
            }
        }
    }

    /**
     * use {@link XPreferences} please
     */
    @Deprecated
    public static <K extends Enum> void updateSetting(Context context, K key, Object value) {
        updateSetting(context, getSharedPreferences(context, key), key, value);
    }

    /**
     * use {@link XPreferences} please
     */
    @Deprecated
    private static <K extends Enum> void updateSetting(Context context, SharedPreferences preferences, K key, @Nullable Object value) {
        SharedPreferences.Editor edit = preferences.edit();
        String keyName = key.name();
        if (value != null) {
            if (gson == null) gson = new Gson();
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

    private static <K extends Enum> SharedPreferences getSharedPreferences(Context context, K key) {
        return context.getSharedPreferences(key.getClass().getName(), Activity.MODE_PRIVATE);
    }

    /**
     * use {@link XPreferences} please
     */
    @Deprecated
    public static <K extends Enum> void clearSettings(final Context context, K key) {
        SharedPreferences preferences = getSharedPreferences(context, key);
        preferences.edit().clear().apply();
    }

    //endregion
    //region init
    private static Gson gson;

    public static void setGson(Gson gson) {
        XCommonBase.gson = gson;
    }

    static Boolean debug;

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

    public static File getStaticDirectory(Context context) {
        File sdcard = Environment.getExternalStorageDirectory();
        return new File(sdcard, "Android/static/" + context.getPackageName());
    }

    public static boolean checkSelfPermission(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
