package com.dhy.xintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

/**
 * Created by donald on 2016/2/2.
 */
public class XIntent extends Intent {
	public XIntent(Context packageContext, Class<?> cls, Serializable... serializable) {
		super(packageContext, cls);
		putSerializableExtra(this, serializable);
	}

	private static void putSerializableExtra(Intent intent, Serializable... serializable) {
		String className = intent.getComponent().getClassName();
		intent.putExtra(className, serializable.length > 1 ? serializable : serializable[0]);
	}

	public static void putSerializableExtra(Activity activity, Serializable... serializable) {
		putSerializableExtra(activity.getIntent(), serializable.length > 1 ? serializable : serializable[0]);
	}

	public static Serializable readSerializableExtra(Activity activity) {
		return activity.getIntent().getSerializableExtra(activity.getClass().getName());
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

	public static <T extends Serializable> T readSerializableExtra(Activity activity, Class<T> cls, int index, T defaultValue) {
		Serializable serializable = readSerializableExtra(activity);
		if (serializable instanceof Object[]) {
			Object[] data = (Object[]) serializable;
			if (index < data.length) return cls.cast(data[index]);
		} else if (index == 0) return cls.cast(serializable);
		return defaultValue;
	}
}
