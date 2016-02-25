package com.dhy.xintent;

import java.util.List;

/**
 * Created by donald on 2016/1/28.
 */
public class Impload {
	public interface Imploadable {
		String getImoploadString();
	}

	public static String getString(List<? extends Imploadable> list, final char split) {
		if (list != null && !list.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (Imploadable i : list) {
				sb.append(i.getImoploadString()).append(split);
			}
			if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
		return "";
	}

	public static String getString(List<? extends Imploadable> list) {
		return getString(list, ',');
	}
}
