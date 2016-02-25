package com.dhy.xintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

/**
 * Created by donald on 2016/2/2.
 */
public class XIntent extends Intent {
	public static final String KEY_EXTRA = XIntent.class.getName();

	public XIntent(Context packageContext, Class<?> cls, Serializable... serializable) {
		super(packageContext, cls);
		putSerializableExtra(this, serializable);
	}

	public static void putSerializableExtra(Intent intent, Serializable... serializable) {
		intent.putExtra(KEY_EXTRA, serializable.length > 1 ? serializable : serializable[0]);
	}


	public static void putSerializableExtra(Activity activity, Serializable... serializable) {
		putSerializableExtra(activity.getIntent(), serializable);
	}

	/**
	 * key = XIntent's class name
	 */
	public static Serializable readSerializableExtra(Activity activity) {
		return activity.getIntent().getSerializableExtra(KEY_EXTRA);
	}

	public static <T extends Serializable> T readSerializableExtra(Activity activity, Class<T> cls) {
		return readSerializableExtra(activity, cls, null);
	}

	public static <T extends Serializable> T readSerializableExtra(Activity activity, Class<T> cls, T defaultValue) {
		Serializable serializable = readSerializableExtra(activity);
		if (serializable instanceof Object[]) {
			Object[] data = (Object[]) serializable;
			for (Object d : data) {
				if (cls.isInstance(d)) {
					return cls.cast(d);
				}
			}
		} else if (cls.isInstance(serializable)) return cls.cast(serializable);
		return defaultValue;
	}

	/**
	 * @return null if not found
	 */
	public static Serializable readSerializableExtra(Activity activity, int index) {
		return readSerializableExtra(activity, Serializable.class, index, null);
	}

	public static <T extends Serializable> T readSerializableExtra(Activity activity, Class<T> cls, int index, T defaultValue) {
		Serializable serializable = readSerializableExtra(activity);
		if (serializable instanceof Object[]) {
			Object[] data = (Object[]) serializable;
			if (index < data.length) return cls.cast(data[index]);
		} else if (index == 0) return cls.cast(serializable);
		return defaultValue;
	}
}
