package com.dhy.xintent;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * use to store common settings, such as login response
 */
public abstract class ICommonSettings<T> {
	protected final transient Context context;
	private final transient Class<T> dataClass;
	private final transient String SETTING_NAME;
	private transient boolean transientCheck = false;
	private final transient SharedPreferences preferences;
	private static final transient Gson gson = new Gson();
	/**
	 * outter class instance
	 */
	private static final transient String THIS0 = "this$0";

	/**
	 * use transient for unstored field
	 */
	public ICommonSettings(Context context) {
		this.context = context;
		SETTING_NAME = getClass().getName();
		dataClass = (Class<T>) getClass();
		preferences = context.getSharedPreferences(SETTING_NAME, Activity.MODE_PRIVATE);
	}

	private void transientCheck() {
		if (!transientCheck && XCommon.isDebug(context)) {
			Class<?> cls = getClass();
			while (cls != Object.class) {
				for (Field f : cls.getDeclaredFields()) {
					if (!Modifier.isTransient(f.getModifiers())) {
						if (!f.isAccessible()) f.setAccessible(true);
						try {
							Object v = f.get(this);
							if (!f.getName().equals(THIS0) && v != null && !(v instanceof Serializable)) {
								throw new IllegalStateException("Please set transient to unstored field: '" + f.getName() + "'");
							}
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
				cls = cls.getSuperclass();
			}
		}
		transientCheck = true;
	}

	public void save() {
		try {
			transientCheck();
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

	/**
	 * load the stored settings. When there's no stored setting nothing will be changed with current object
	 */
	public T load() {
		T setting = readPreferences(dataClass, null);
		if (setting != null) {
			Class<?> cls = setting.getClass();
			while (cls != ICommonSettings.class) {
				for (Field f : cls.getDeclaredFields()) {
					if (!f.getName().equals(THIS0) && !Modifier.isTransient(f.getModifiers())) {
						try {
							if (!f.isAccessible()) f.setAccessible(true);
							f.set(this, f.get(setting));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				cls = cls.getSuperclass();
			}
		}
		return (T) this;
	}
}
