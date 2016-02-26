package com.dhy.xintent;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;


public final class SIntent extends Intent {


	public static SIntent s(Activity context, Class<? extends Activity> cls, Serializable... serializable) {
		return new SIntent(context, cls, serializable);
	}

	private SIntent(Activity context, Class<? extends Activity> cls, Serializable... serializable) {
		super(context, cls);
		putSerializableExtra(cls.getSimpleName(), this, serializable);
	}

	private static Intent putSerializableExtra(String className, Intent intent, Serializable... serializable) {
		if (serializable.length != 0) {
			intent.putExtra(className, serializable.length > 1 ? serializable : serializable[0]);
		}
		return intent;
	}


	public static Intent putSerializableExtra(Activity activity, Serializable... serializable) {
		return putSerializableExtra(activity.getClass().getSimpleName(), activity.getIntent(), serializable);
	}


	public static Serializable readSerializableExtra(Activity activity) {
		return activity.getIntent().getSerializableExtra(activity.getClass().getSimpleName());
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


	public static <T extends Serializable> T readSerializableExtra(Activity activity, Class<T> cls, int index) {
		return readSerializableExtra(activity, cls, index, null);
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
