package com.dhy.xintent;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Field;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class XCommon {
    //region setTextWithFormat
    public static TextView setTextWithFormat(Activity activity, @IdRes int rid, Object value, final Boolean show) {
        return setTextWithFormat((TextView) activity.findViewById(rid), value, show);
    }

    public static TextView setTextWithFormat(Activity activity, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setTextWithFormat((TextView) activity.findViewById(rid), value, visibility);
    }

    public static TextView setTextWithFormat(Activity activity, @IdRes int rid, Object value) {
        return setTextWithFormat((TextView) activity.findViewById(rid), value);
    }

    public static TextView setTextWithFormat(Dialog dialog, @IdRes int rid, Object value, final Boolean show) {
        return setTextWithFormat((TextView) dialog.findViewById(rid), value, show);
    }

    public static TextView setTextWithFormat(Dialog dialog, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setTextWithFormat((TextView) dialog.findViewById(rid), value, visibility);
    }

    public static TextView setTextWithFormat(Dialog dialog, @IdRes int rid, Object value) {
        return setTextWithFormat((TextView) dialog.findViewById(rid), value);
    }

    public static TextView setTextWithFormat(View view, @IdRes int rid, Object value, final Boolean show) {
        return setTextWithFormat((TextView) view.findViewById(rid), value, show);
    }

    public static TextView setTextWithFormat(View view, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setTextWithFormat((TextView) view.findViewById(rid), value, visibility);
    }

    public static TextView setTextWithFormat(View view, @IdRes int rid, Object value) {
        return setTextWithFormat((TextView) view.findViewById(rid), value);
    }

    public static TextView setTextWithFormat(IFindViewById iFindViewById, @IdRes int rid, Object value, final Boolean show) {
        return setTextWithFormat((TextView) iFindViewById.findViewById(rid), value, show);
    }

    public static TextView setTextWithFormat(IFindViewById iFindViewById, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setTextWithFormat((TextView) iFindViewById.findViewById(rid), value, visibility);
    }

    public static TextView setTextWithFormat(IFindViewById iFindViewById, @IdRes int rid, Object value) {
        return setTextWithFormat((TextView) iFindViewById.findViewById(rid), value);
    }

    public static TextView setTextWithFormat(Fragment fragment, @IdRes int rid, Object value, final Boolean show) {
        return setTextWithFormat((TextView) fragment.getView().findViewById(rid), value, show);
    }

    public static TextView setTextWithFormat(Fragment fragment, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setTextWithFormat((TextView) fragment.getView().findViewById(rid), value, visibility);
    }

    public static TextView setTextWithFormat(Fragment fragment, @IdRes int rid, Object value) {
        return setTextWithFormat((TextView) fragment.getView().findViewById(rid), value);
    }

    public static TextView setTextWithFormat(android.support.v4.app.Fragment fragment, @IdRes int rid, Object value, final Boolean show) {
        return setTextWithFormat((TextView) fragment.getView().findViewById(rid), value, show);
    }

    public static TextView setTextWithFormat(android.support.v4.app.Fragment fragment, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setTextWithFormat((TextView) fragment.getView().findViewById(rid), value, visibility);
    }

    public static TextView setTextWithFormat(android.support.v4.app.Fragment fragment, @IdRes int rid, Object value) {
        return setTextWithFormat((TextView) fragment.getView().findViewById(rid), value);
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

    private static Object getFormatedString(TextView textView, Object value) {
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
    public static TextView setText(Activity activity, @IdRes int rid, Object value, @Nullable Boolean show) {
        return setText((TextView) activity.findViewById(rid), value, show);
    }

    public static TextView setText(Activity activity, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setText((TextView) activity.findViewById(rid), value, visibility);
    }

    public static TextView setText(Activity activity, @IdRes int rid, Object value) {
        return setText((TextView) activity.findViewById(rid), value);
    }

    public static TextView setText(Dialog dialog, @IdRes int rid, Object value, @Nullable Boolean show) {
        return setText((TextView) dialog.findViewById(rid), value, show);
    }

    public static TextView setText(Dialog dialog, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setText((TextView) dialog.findViewById(rid), value, visibility);
    }

    public static TextView setText(Dialog dialog, @IdRes int rid, Object value) {
        return setText((TextView) dialog.findViewById(rid), value);
    }

    public static TextView setText(View view, @IdRes int rid, Object value, @Nullable Boolean show) {
        return setText((TextView) view.findViewById(rid), value, show);
    }

    public static TextView setText(View view, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setText((TextView) view.findViewById(rid), value, visibility);
    }

    public static TextView setText(View view, @IdRes int rid, Object value) {
        return setText((TextView) view.findViewById(rid), value);
    }

    public static TextView setText(IFindViewById iFindViewById, @IdRes int rid, Object value, @Nullable Boolean show) {
        return setText((TextView) iFindViewById.findViewById(rid), value, show);
    }

    public static TextView setText(IFindViewById iFindViewById, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setText((TextView) iFindViewById.findViewById(rid), value, visibility);
    }

    public static TextView setText(IFindViewById iFindViewById, @IdRes int rid, Object value) {
        return setText((TextView) iFindViewById.findViewById(rid), value);
    }

    public static TextView setText(Fragment fragment, @IdRes int rid, Object value, @Nullable Boolean show) {
        return setText((TextView) fragment.getView().findViewById(rid), value, show);
    }

    public static TextView setText(Fragment fragment, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setText((TextView) fragment.getView().findViewById(rid), value, visibility);
    }

    public static TextView setText(Fragment fragment, @IdRes int rid, Object value) {
        return setText((TextView) fragment.getView().findViewById(rid), value);
    }

    public static TextView setText(android.support.v4.app.Fragment fragment, @IdRes int rid, Object value, @Nullable Boolean show) {
        return setText((TextView) fragment.getView().findViewById(rid), value, show);
    }

    public static TextView setText(android.support.v4.app.Fragment fragment, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setText((TextView) fragment.getView().findViewById(rid), value, visibility);
    }

    public static TextView setText(android.support.v4.app.Fragment fragment, @IdRes int rid, Object value) {
        return setText((TextView) fragment.getView().findViewById(rid), value);
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
    //	endregion

    //region setImage
    public static ImageView setImage(Activity activity, @IdRes int rid, @DrawableRes int image, @Nullable final Boolean show) {
        return setImage((ImageView) activity.findViewById(rid), image, show);
    }

    public static ImageView setImage(Activity activity, @IdRes int rid, @DrawableRes int image, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) activity.findViewById(rid), image, visibility);
    }

    public static ImageView setImage(Activity activity, @IdRes int rid, @DrawableRes int image) {
        return setImage((ImageView) activity.findViewById(rid), image);
    }

    public static ImageView setImage(Activity activity, @IdRes int rid, Uri uri, @Nullable final Boolean show) {
        return setImage((ImageView) activity.findViewById(rid), uri, show);
    }

    public static ImageView setImage(Activity activity, @IdRes int rid, Uri uri, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) activity.findViewById(rid), uri, visibility);
    }

    public static ImageView setImage(Activity activity, @IdRes int rid, Uri uri) {
        return setImage((ImageView) activity.findViewById(rid), uri);
    }

    public static ImageView setImage(Dialog dialog, @IdRes int rid, @DrawableRes int image, @Nullable final Boolean show) {
        return setImage((ImageView) dialog.findViewById(rid), image, show);
    }

    public static ImageView setImage(Dialog dialog, @IdRes int rid, @DrawableRes int image, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) dialog.findViewById(rid), image, visibility);
    }

    public static ImageView setImage(Dialog dialog, @IdRes int rid, @DrawableRes int image) {
        return setImage((ImageView) dialog.findViewById(rid), image);
    }

    public static ImageView setImage(Dialog dialog, @IdRes int rid, Uri uri, @Nullable final Boolean show) {
        return setImage((ImageView) dialog.findViewById(rid), uri, show);
    }

    public static ImageView setImage(Dialog dialog, @IdRes int rid, Uri uri, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) dialog.findViewById(rid), uri, visibility);
    }

    public static ImageView setImage(Dialog dialog, @IdRes int rid, Uri uri) {
        return setImage((ImageView) dialog.findViewById(rid), uri);
    }

    public static ImageView setImage(View view, @IdRes int rid, @DrawableRes int image, @Nullable final Boolean show) {
        return setImage((ImageView) view.findViewById(rid), image, show);
    }

    public static ImageView setImage(View view, @IdRes int rid, @DrawableRes int image, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) view.findViewById(rid), image, visibility);
    }

    public static ImageView setImage(View view, @IdRes int rid, @DrawableRes int image) {
        return setImage((ImageView) view.findViewById(rid), image);
    }

    public static ImageView setImage(View view, @IdRes int rid, Uri uri, @Nullable final Boolean show) {
        return setImage((ImageView) view.findViewById(rid), uri, show);
    }

    public static ImageView setImage(View view, @IdRes int rid, Uri uri, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) view.findViewById(rid), uri, visibility);
    }

    public static ImageView setImage(View view, @IdRes int rid, Uri uri) {
        return setImage((ImageView) view.findViewById(rid), uri);
    }

    public static ImageView setImage(IFindViewById iFindViewById, @IdRes int rid, @DrawableRes int image, @Nullable final Boolean show) {
        return setImage((ImageView) iFindViewById.findViewById(rid), image, show);
    }

    public static ImageView setImage(IFindViewById iFindViewById, @IdRes int rid, @DrawableRes int image, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) iFindViewById.findViewById(rid), image, visibility);
    }

    public static ImageView setImage(IFindViewById iFindViewById, @IdRes int rid, @DrawableRes int image) {
        return setImage((ImageView) iFindViewById.findViewById(rid), image);
    }

    public static ImageView setImage(IFindViewById iFindViewById, @IdRes int rid, Uri uri, @Nullable final Boolean show) {
        return setImage((ImageView) iFindViewById.findViewById(rid), uri, show);
    }

    public static ImageView setImage(IFindViewById iFindViewById, @IdRes int rid, Uri uri, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) iFindViewById.findViewById(rid), uri, visibility);
    }

    public static ImageView setImage(IFindViewById iFindViewById, @IdRes int rid, Uri uri) {
        return setImage((ImageView) iFindViewById.findViewById(rid), uri);
    }

    public static ImageView setImage(Fragment fragment, @IdRes int rid, @DrawableRes int image, @Nullable final Boolean show) {
        return setImage((ImageView) fragment.getView().findViewById(rid), image, show);
    }

    public static ImageView setImage(Fragment fragment, @IdRes int rid, @DrawableRes int image, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) fragment.getView().findViewById(rid), image, visibility);
    }

    public static ImageView setImage(Fragment fragment, @IdRes int rid, @DrawableRes int image) {
        return setImage((ImageView) fragment.getView().findViewById(rid), image);
    }

    public static ImageView setImage(Fragment fragment, @IdRes int rid, Uri uri, @Nullable final Boolean show) {
        return setImage((ImageView) fragment.getView().findViewById(rid), uri, show);
    }

    public static ImageView setImage(Fragment fragment, @IdRes int rid, Uri uri, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) fragment.getView().findViewById(rid), uri, visibility);
    }

    public static ImageView setImage(Fragment fragment, @IdRes int rid, Uri uri) {
        return setImage((ImageView) fragment.getView().findViewById(rid), uri);
    }

    public static ImageView setImage(android.support.v4.app.Fragment fragment, @IdRes int rid, @DrawableRes int image, @Nullable final Boolean show) {
        return setImage((ImageView) fragment.getView().findViewById(rid), image, show);
    }

    public static ImageView setImage(android.support.v4.app.Fragment fragment, @IdRes int rid, @DrawableRes int image, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) fragment.getView().findViewById(rid), image, visibility);
    }

    public static ImageView setImage(android.support.v4.app.Fragment fragment, @IdRes int rid, @DrawableRes int image) {
        return setImage((ImageView) fragment.getView().findViewById(rid), image);
    }

    public static ImageView setImage(android.support.v4.app.Fragment fragment, @IdRes int rid, Uri uri, @Nullable final Boolean show) {
        return setImage((ImageView) fragment.getView().findViewById(rid), uri, show);
    }

    public static ImageView setImage(android.support.v4.app.Fragment fragment, @IdRes int rid, Uri uri, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) fragment.getView().findViewById(rid), uri, visibility);
    }

    public static ImageView setImage(android.support.v4.app.Fragment fragment, @IdRes int rid, Uri uri) {
        return setImage((ImageView) fragment.getView().findViewById(rid), uri);
    }

    private static ImageView setImage(ImageView imageView, @DrawableRes int image, @Nullable final Boolean show) {
        return setImage(imageView, image, show == null ? null : (show ? VISIBLE : GONE));
    }

    private static ImageView setImage(ImageView imageView, @DrawableRes int image, @Visibility @Nullable final Integer visibility) {
        if (visibility != null) imageView.setVisibility(visibility);
        return setImage(imageView, image);
    }

    private static ImageView setImage(ImageView imageView, @DrawableRes int image) {
        imageView.setImageResource(image);
        return imageView;
    }

    private static ImageView setImage(ImageView imageView, Uri uri, @Nullable final Boolean show) {
        return setImage(imageView, uri, show == null ? null : (show ? VISIBLE : GONE));
    }

    private static ImageView setImage(ImageView imageView, Uri uri, @Visibility @Nullable final Integer visibility) {
        if (visibility != null) imageView.setVisibility(visibility);
        return setImage(imageView, uri);
    }

    private static ImageView setImage(ImageView imageView, Uri uri) {
        imageView.setImageURI(uri);
        return imageView;
    }

    //	endregion

    //region Setting
    @Nullable
    public static <K extends Enum> String getSetting(Context context, K key) {
        return getSetting(context, getSharedPreferences(context, key), key, String.class, null);
    }

    @Nullable
    public static <K extends Enum, V> V getSetting(Context context, K key, Class<V> dataClass) {
        return getSetting(context, getSharedPreferences(context, key), key, dataClass, null);
    }

    public static <K extends Enum, V> V getSetting(Context context, K key, Class<V> dataClass, @Nullable V defValue) {
        return getSetting(context, getSharedPreferences(context, key), key, dataClass, defValue);
    }

    private static <K extends Enum, V> V getSetting(Context context, SharedPreferences sharedPreferences, K key, Class<V> dataClass, @Nullable V defValue) {
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
                return defValue;
            }
        }
    }

    /**
     * @param key   view, id, name, activity
     * @param value string, int, boolean, float, double, long, data object, or null to remove setting
     */
    public static <K extends Enum> void updateSetting(Context context, K key, Object value) {
        updateSetting(context, getSharedPreferences(context, key), key, value);
    }

    /**
     * @param value pass null to remove setting
     */
    private static <K extends Enum> void updateSetting(Context context, SharedPreferences preferences, K key, @Nullable Object value) {
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

    private static <K extends Enum> SharedPreferences getSharedPreferences(Context context, K key) {
        return context.getSharedPreferences(key.getClass().getName(), Activity.MODE_PRIVATE);
    }

    public static <K extends Enum> void clearSettings(final Context context, K key) {
        SharedPreferences preferences = getSharedPreferences(context, key);
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
