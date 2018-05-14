package com.dhy.xintent.preferences;

import com.dhy.xintent.util.BaseActivityUnitTestCase;

public class JsonPreferencesTest extends BaseActivityUnitTestCase {
    private KEY key = KEY.key;

    public void testInnerPreferences() {
        Long v, stored;
        JsonPreferences.set(context, key, false, v = System.currentTimeMillis());
        stored = JsonPreferences.get(context, key, false, Long.class);
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

    public enum KEY {
        key
    }
}
