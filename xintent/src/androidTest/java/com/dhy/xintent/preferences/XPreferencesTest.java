package com.dhy.xintent.preferences;

import com.dhy.xintent.XCommon;
import com.dhy.xintent.util.BaseActivityUnitTestCase;

public class XPreferencesTest extends BaseActivityUnitTestCase {
    private KEY key = KEY.key;

    public void testInnerPreferences() {
        Long v, stored;
        XPreferences.set(context, key, false, v = System.currentTimeMillis());
        stored = XPreferences.get(context, key, false, Long.class);
        assertEquals(v, stored);
    }

    public void testStaticPreferences() {
        Long v = System.currentTimeMillis();
        StaticPreferences preferences = new StaticPreferences(KEY.class, key, context.getFilesDir());
        preferences.set(key, v);
        preferences.apply();

        preferences = new StaticPreferences(KEY.class, key, context.getFilesDir());
        Long stored = preferences.get(key, Long.class);
        preferences.exit();

        assertEquals(v, stored);
    }

    public void testCompatibleWithXCommon() {
        Long v, stored;
        XPreferences.set(context, key, false, v = System.currentTimeMillis());
        //noinspection deprecation
        stored = XCommon.getSetting(context, key, Long.class);
        assertEquals(v, stored);
    }

    public enum KEY {
        key
    }
}
