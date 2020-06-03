package com.dhy.xintent.util;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;

public class BaseActivityUnitTestCase {
    protected Context context;

    @Before
    public void initContext() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}
