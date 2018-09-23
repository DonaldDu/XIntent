package com.dhy.xintent.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ReplacementSpan;

public class SplitSpan extends ReplacementSpan {
    private final String split;

    public SplitSpan(String split) {
        this.split = split;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return (int) (paint.measureText(text, start, end) + paint.measureText(split));
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        canvas.drawText(split, x, y, paint);
        canvas.drawText(text, start, end, x + paint.measureText(split), y, paint);
    }

    public static SpannableString getSpitedText(String text, final int[] parts, final String split) {
        SpannableString spannable = new SpannableString(text);
        int start = 0, max = text.length();
        for (int i = 0; i < parts.length; i++) {
            if (start != 0) {
                Object style = new SplitSpan(split);
                spannable.setSpan(style, start, start + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            start += parts[i];
            if (start >= max) break;
        }
        return spannable;
    }
}
