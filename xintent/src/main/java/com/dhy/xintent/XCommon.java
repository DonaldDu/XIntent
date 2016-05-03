package com.dhy.xintent;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.lang.reflect.Field;

public class XCommon {
	static final String tag = "XCommon";
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
	public static TextView setTextWithFormat(Object container, int rid, Object value, final boolean show) {
		return setTextWithFormat(findViewById(container, rid), value, show);
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
	public static TextView setTextWithFormat(TextView textView, Object value, final boolean show) {
		return setTextWithFormat(textView, value, show ? View.VISIBLE : View.GONE);
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
		String format = getFormat(textView);
		if (value instanceof Number) {
			textView.setText(String.format(format, value));
		} else {//string
			if (value instanceof Object[]) {
				textView.setText(String.format(format, (Object[]) value));
			} else {
				textView.setText(String.format(format, (value != null) ? value : ""));
			}
		}
		return textView;
	}

	private static String getFormat(TextView textView) {
		try {
			return textView.getContentDescription().toString();
		} catch (Exception e) {
			throw new IllegalArgumentException("no format string found, check view's description please.");
		}
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

	//region Activity Setting

	/**
	 * with key of activity class name
	 */
	public static String getSetting(Activity activity) {
		return getSetting(activity, activity);
	}

	public static String getSetting(Activity activity, Object key) {
		return getSetting(activity, key, String.class, null);
	}

	public static <T> T getSetting(Activity activity, Object key, Class<T> dataClass) {
		return getSetting(activity, key, dataClass, null);
	}

	public static <T> T getSetting(Activity activity, Class<T> dataClass) {
		return getSetting(activity, activity, dataClass, null);
	}

	public static <T> T getSetting(Activity activity, Class<T> dataClass, @Nullable T defValue) {
		return getSetting(activity, activity, dataClass, defValue);
	}

	public static <T> T getSetting(Activity activity, Object key, Class<T> dataClass, @Nullable T defValue) {
		return getSetting(activity, getSharedPreferences(activity), key, dataClass, defValue);
	}

	public static <T> T getSetting(Context context, SharedPreferences sharedPreferences, Object key, Class<T> dataClass, @Nullable T defValue) {
		String keyName = getKeyName(context, key);
		if (TextUtils.isEmpty(keyName)) return null;
		String value = sharedPreferences.getString(keyName, null);
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
	 * with key of activity class name
	 */
	public static void updateSetting(Activity activity, Object value) {
		updateSetting(activity, activity, value);
	}

	/**
	 * @param key   view, id, name, activity
	 * @param value string, int, boolean, float, double, long, data object, or null to remove the setting
	 */
	public static void updateSetting(Activity activity, Object key, Object value) {
		updateSetting(activity, getSharedPreferences(activity), key, value);
	}

	public static void updateSetting(Context context, SharedPreferences sharedPreferences, Object key, Object value) {
		SharedPreferences.Editor edit = sharedPreferences.edit();
		String keyName = getKeyName(context, key);
		if (TextUtils.isEmpty(keyName)) return;
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

	public static void deleteSettings(final Activity activity) {
		SharedPreferences preferences = getSharedPreferences(activity);
		preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
			@Override
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
				File f = new File(activity.getFilesDir().getParent() + "/shared_prefs/" + activity.getClass().getName() + ".xml");
				if (f.exists()) {
					f.delete();
				}
			}
		});
		preferences.edit().clear().apply();
	}

	/**
	 * @param key view, id, name, activity
	 */
	private static String getKeyName(Context context, Object key) {
		String keyName;
		if (key instanceof String) {
			keyName = (String) key;
		} else if (key instanceof Activity) {
			keyName = context.getClass().getName();
		} else if (key instanceof View) {
			keyName = String.valueOf(((View) key).getId());
		} else if (key instanceof Integer) {
			keyName = String.valueOf(key);
		} else {
			if (isDebug(context)) {
				Toast.makeText(context, "setting key error", Toast.LENGTH_LONG).show();
				Log.e(tag, context.getClass().getName() + ",key " + key);
			}
			keyName = null;
		}
		return keyName;
	}

	private static SharedPreferences getSharedPreferences(Activity activity) {
		return activity.getPreferences(Activity.MODE_PRIVATE);
	}

	//endregion

	//region init
	private static Gson gson;
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

	static {
		gson = new Gson();
	}
//endregion
}
