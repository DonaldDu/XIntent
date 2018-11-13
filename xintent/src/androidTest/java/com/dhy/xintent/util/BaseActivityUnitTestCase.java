package com.dhy.xintent.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;

public class BaseActivityUnitTestCase {
    protected Context context;

    @Before
    public void initContext() {
        context = InstrumentationRegistry.getTargetContext();
    }
}
