package com.dhy.xintent.preferences;

import com.dhy.xintent.XCommon;
import com.dhy.xintent.util.BaseActivityUnitTestCase;

import java.util.ArrayList;
import java.util.List;

import junitx.framework.ListAssert;

import static junit.framework.TestCase.assertEquals;

public class XPreferencesTest extends BaseActivityUnitTestCase {
    private KEY key = KEY.key;

    public void testInnerPreferences() {
        Long v = System.currentTimeMillis();
        XPreferences.set(context, key, false, v);
        Long stored = XPreferences.get(context, key, false, Long.class);
        assertEquals(v, stored);
    }

    public void testStaticPreferences() {
        Long v = System.currentTimeMillis();
        XPreferences preferences;

        preferences = new XPreferences(key, new StaticPreferencesStore(context.getCacheDir(), "test"));
        preferences.set(v).apply().exit();

        preferences = new XPreferences(key, new StaticPreferencesStore(context.getCacheDir(), "test"));
        Long stored = preferences.get(Long.class);
        assertEquals(v, stored);
    }

    public void test_getList() {
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(System.currentTimeMillis()));
        list.add(String.valueOf(System.currentTimeMillis() * 2));

        XPreferences.set(context, key, list);
        List<String> stored = XPreferences.getList(context, key);

        ListAssert.assertEquals(list, stored);
    }

    public void testCompatibleWithXCommon() {
        Long v, stored;
        XPreferences.set(context, key, false, v = System.currentTimeMillis());
        //noinspection deprecation
        stored = XCommon.getSetting(context, key, Long.class);
        assertEquals(v, stored);

        //noinspection deprecation
        XCommon.updateSetting(context, key, v = System.currentTimeMillis());
        stored = XPreferences.get(context, key, Long.class);
        assertEquals(v, stored);
    }

    public enum KEY {
        key
    }
}
