package com.dhy.xintent;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Field;

/**
 * use to store common settings, such as login response
 */
public abstract class ICommonSettings<T> {
	protected Context context;
	private final String SETTING_NAME;
	private final SharedPreferences preferences;

	/**
	 * @param annotation fixed value = {@link Expose}.class, you need annotate the field with {@link Expose} to store it
	 */
	public ICommonSettings(Context context, @NonNull final Class annotation) {
		if (XCommon.isDebug(context) && !annotation.equals(Expose.class)) {
			throw new IllegalArgumentException("you need annotate the field with Expose to store it");
		}
		this.context = context;
		SETTING_NAME = getClass().getName();
		preferences = context.getSharedPreferences(SETTING_NAME, Activity.MODE_PRIVATE);
	}

	public void save() {
		try {
			preferences.edit().putString(SETTING_NAME, gson.toJson(this)).apply();
		} catch (Exception e) {
			if (XCommon.isDebug(context)) {
				throw e;
			}
		}
	}

	private T readPreferences(Class<T> dataClass, @Nullable T defValue) {
		String json = preferences.getString(SETTING_NAME, null);
		if (json != null) {
			try {
				return gson.fromJson(json, dataClass);
			} catch (Exception e) {
				if (XCommon.isDebug(context)) {
					throw e;
				}
			}
		}
		return defValue;
	}

	public abstract T load();

	protected T load(T container, Class<T> dataClass, @Nullable T defValue) {
		T setting = readPreferences(dataClass, defValue);
		if (setting != null) {
			Class<?> cls = setting.getClass();
			while (cls != ICommonSettings.class) {
				for (Field f : cls.getDeclaredFields()) {
					if (isExpose(f)) {
						try {
							if (!f.isAccessible()) f.setAccessible(true);
							f.set(container, f.get(setting));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				cls = cls.getSuperclass();
			}
		}
		return container;
	}

	private boolean isExpose(Field f) {
		Expose expose = f.getAnnotation(Expose.class);
		return expose != null && expose.deserialize();
	}

	private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
}
