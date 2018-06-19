package com.dhy.xintentsample;

import android.app.Activity;
import android.os.Bundle;

import com.dhy.xintent.preferences.XPreferences;

/**
 * Created by donald on 2016/2/5.
 */
public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_layout);
        XPreferences preferences = new XPreferences(this, Key.key, true);
        preferences.set(1).apply().exit();
    }

    enum Key {
        key
    }
}
