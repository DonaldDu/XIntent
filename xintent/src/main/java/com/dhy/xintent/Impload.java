package com.dhy.xintent;

import android.support.annotation.NonNull;

import com.dhy.xintent.interfaces.Imploadable;

import java.util.List;

public class Impload {
    @NonNull
    public static String getString(List<? extends Imploadable> list) {
        return getString(list, ",");
    }

    @NonNull
    public static String getString(List<? extends Imploadable> list, final String split) {
        if (list != null && !list.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Imploadable i : list) {
                sb.append(split).append(i.getImoploadString());
            }
            if (sb.length() > 0) sb.deleteCharAt(0);
            return sb.toString();
        }
        return "";
    }
}
