package com.dhy.xintent;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.dhy.xintent.annotation.GenCode;
import com.dhy.xintent.annotation.Visibility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;

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
        if (visibility != null && textView != null) textView.setVisibility(visibility);
        return setText(textView, value);
    }

    public static TextView setText(TextView textView, Object value) {
        if (textView != null) textView.setText(value != null ? String.valueOf(value) : "");
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
        if (visibility != null && imageView != null) imageView.setVisibility(visibility);
        return setImage(imageView, image);
    }

    static ImageView setImage(ImageView imageView, @DrawableRes int image) {
        if (imageView != null) imageView.setImageResource(image);
        return imageView;
    }

    static ImageView setImage(ImageView imageView, Uri uri, @Nullable final Boolean show) {
        return setImage(imageView, uri, show == null ? null : (show ? VISIBLE : GONE));
    }

    static ImageView setImage(ImageView imageView, Uri uri, @Visibility @Nullable final Integer visibility) {
        if (visibility != null && imageView != null) imageView.setVisibility(visibility);
        return setImage(imageView, uri);
    }

    static ImageView setImage(ImageView imageView, Uri uri) {
        if (imageView != null) imageView.setImageURI(uri);
        return imageView;
    }
    //endregion

    //region init
    static Boolean debug;

    /**
     * get application's BuildConfig.DEBUG; use Context.isDebugable instead
     */
    @Deprecated
    public static boolean isDebug(Context context) {
        if (debug != null) return debug;
        try {
            Class<?> BuildConfig = Class.forName(context.getPackageName() + ".BuildConfig");
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

    public static boolean isMainApplication(Context context) {
        String processName = getProcessName();
        return (processName == null || context.getPackageName().equals(processName));
    }

    @Nullable
    private static String getProcessName() {
        try {
            int pid = android.os.Process.myPid();
            BufferedReader cmd = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String name = cmd.readLine().trim();
            cmd.close();
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
