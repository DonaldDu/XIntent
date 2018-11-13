package com.dhy.xintent;

import android.widget.TextView;

import com.dhy.xintent.util.BaseActivityUnitTestCase;

import junit.framework.TestCase;

public class SetTextUtilTest extends BaseActivityUnitTestCase {
    private TextView textView;

    public void testSetText() {
        textView = new TextView(context);
        check("price%d", 1, "price1");
        check("price%s", 1, "price1");
        check("price%s", null, "price");
        check("price%1$.1f,price%2$.2f", new Object[]{1.1, 2.2}, "price1.1,price2.20");
    }

    void check(String format, Object input, String output) {
        textView.setContentDescription(format);
        XCommon.setTextWithFormat(textView, input);
        TestCase.assertEquals(output, textView.getText().toString());
    }
}
