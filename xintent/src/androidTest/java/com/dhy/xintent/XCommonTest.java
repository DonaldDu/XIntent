package com.dhy.xintent;

import android.widget.TextView;

import com.dhy.xintent.util.BaseActivityUnitTestCase;

public class XCommonTest extends BaseActivityUnitTestCase {
    public void testContentDescriptionIsNotString() {
        TextView textView = new TextView(context);
        CharSequence description = textView.getContentDescription();
        assertFalse("description instanceof String", description instanceof String);
    }

    @SuppressWarnings("ConstantConditions")
    public void testTextViewTagNoDefaultType() {
        TextView textView = new TextView(context);
        Object tag = textView.getTag();
        Integer i = (Integer) tag;
        assertNull(i);
        String s = (String) tag;
        assertNull(s);
    }
}
