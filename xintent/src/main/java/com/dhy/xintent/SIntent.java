package com.dhy.xintent;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.io.Serializable;

/**
 * <h3>自定义Intent对象 简化activity之间传值</h3>
 * @author tuzhao
 *         2016-02-15
 */
public final class SIntent extends Intent {

	/**
	 * 根据此方法获取SIntent对象
	 * @param context      Activity  当前界面activity对象
	 * @param cls          Class<? extends Activity> 要启动的activity的类对象
	 * @param serializable Serializable[]  要传递的值
	 * @return SIntent  SIntent extends Intent
	 */
	public static SIntent s(Activity context, Class<? extends Activity> cls, Serializable... serializable) {
		return new SIntent(context, cls, serializable);
	}

	private SIntent(Activity context, Class<? extends Activity> cls, Serializable... serializable) {
		super(context, cls);
		putSerializableExtra(this, serializable);
	}

	private static Intent putSerializableExtra(Intent intent, Serializable... serializable) {
		String className = intent.getComponent().getClassName();
		it("className", className);
		intent.putExtra(className, serializable.length > 1 ? serializable : serializable[0]);
		return intent;
	}

	/**
	 * @param activity     Activity
	 * @param serializable 要传递序列化值
	 * @return 当前activity中所持有的intent对象
	 * @deprecated use this to start same activity
	 */
	public static Intent putSerializableExtra(Activity activity, Serializable... serializable) {
		return putSerializableExtra(activity.getIntent(), serializable);
	}

	/**
	 * <h3>读取当前界面传递过来的序列化数组</h3>
	 * @param activity Activity 当前界面activity对象
	 * @return 序列化数组
	 */
	public static Serializable readSerializableExtra(Activity activity) {
		return activity.getIntent().getSerializableExtra(activity.getClass().getName());
	}

	/**
	 * <h3>根绝类对象读取传递过来序列化数组中的值</h3>
	 * @param activity Activity 当前界面activity对象
	 * @param cls      值的类对象
	 * @return 在序列化数组中第一个与指定类对象匹配的值 默认返回null
	 */
	public static <T extends Serializable> T readSerializableExtra(Activity activity, Class<T> cls) {
		return readSerializableExtra(activity, cls, null);
	}

	/**
	 * <h3>根绝类对象读取传递过来序列化数组中的值</h3>
	 * @param activity     Activity 当前界面activity对象
	 * @param cls          值的类对象
	 * @param defaultValue 值的类对象的默认值
	 * @return 在序列化数组中第一个与指定类对象匹配的值
	 */
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
	 * <h3>读取传递过来序列化数组中指定位置的值</h3>
	 * @param activity Activity 当前界面activity对象
	 * @param cls      指定位置值的类对象
	 * @param index    指定位置值在系列化数组中的下标位置
	 * @return T extends Serializable  指定位置值的值  默认返回null
	 */
	public static <T extends Serializable> T readSerializableExtra(Activity activity, Class<T> cls, int index) {
		return readSerializableExtra(activity, cls, index, null);
	}

	/**
	 * <h3>读取传递过来序列化数组中指定位置的值</h3>
	 * @param activity     Activity 当前界面activity对象
	 * @param cls          指定位置值的类对象
	 * @param index        指定位置值在系列化数组中的下标位置
	 * @param defaultValue 指定位置值的默认值
	 * @return T extends Serializable  指定位置值的值
	 */
	public static <T extends Serializable> T readSerializableExtra(Activity activity, Class<T> cls, int index, T defaultValue) {
		Serializable serializable = readSerializableExtra(activity);
		if (serializable instanceof Object[]) {
			Object[] data = (Object[]) serializable;
			if (index < data.length) return cls.cast(data[index]);
		} else if (index == 0) return cls.cast(serializable);
		return defaultValue;
	}

	private static void it(String tag, Object msg) {
		if (BuildConfig.DEBUG) {
			Log.i("TAG", tag + "-->" + String.valueOf(msg));
		}
	}

}
