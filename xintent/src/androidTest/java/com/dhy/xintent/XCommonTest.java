package com.dhy.xintent;

import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.dhy.xintent.util.BaseActivityUnitTestCase;

import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class XCommonTest extends BaseActivityUnitTestCase {

    public void testContentDescriptionIsNotString() {
        TextView textView = new TextView(context);
        CharSequence description = textView.getContentDescription();
        assertFalse("description instanceof String", description instanceof String);
    }
}
