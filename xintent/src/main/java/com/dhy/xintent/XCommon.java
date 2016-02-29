package com.dhy.xintent;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class XCommon {
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
	 * @param container must be Activity ,View or IFindViewById
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

	/**
	 * @param container must be Activity ,View or IFindViewById
	 */
	private static TextView findViewById(Object container, int rid) {
		TextView textView;
		if (container instanceof Activity) {
			textView = (TextView) ((Activity) container).findViewById(rid);
		} else if (container instanceof View) {
			textView = (TextView) ((View) container).findViewById(rid);
		} else if (container instanceof IFindViewById) {
			textView = (TextView) ((IFindViewById) container).findViewById(rid);
		} else {
			throw new IllegalArgumentException("container must be Activity ,View or IFindViewById");
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
}
