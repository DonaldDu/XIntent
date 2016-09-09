package com.dhy.xintentsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dhy.xintent.XCommon;
import com.dhy.xintent.XIntent;

public class XIntentSampleActivity extends Activity {
    String tag = XIntentSampleActivity.class.getSimpleName();
    boolean init = false;
    Context context;
    int hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        final String msg = XIntent.readSerializableExtra(this, String.class);
        XCommon.setTextWithFormat(this, R.id.textView, msg);

        if (savedInstanceState != null) {
            Intent intent = new XIntent(savedInstanceState);
            hash = XIntent.readSerializableExtra(intent, Integer.class, -1);
        }
        Log.i(tag, "init " + init);
        Log.i(tag, "hash1 " + hash);
        init = true;
        hash = findViewById(R.id.textView).hashCode();
        Log.i(tag, "hash2 " + hash);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        XIntent.putSerializableExtra(outState, "s", 1);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Intent intent = new XIntent(savedInstanceState);
        String a = XIntent.readSerializableExtra(intent, String.class, "");
        Integer b = XIntent.readSerializableExtra(intent, Integer.class, 0);
    }

    public void startIntent(View view) {
        final EditText editText = (EditText) findViewById(R.id.editText);
        String text = editText.getText().toString();
        if (text.length() > 2) {
            XIntent.startActivity(context, XIntentSampleActivity.class, text);
        } else {
            Intent intent = new XIntent(context, XIntentSampleActivity.class, text);
            startActivity(intent);
        }
    }

    public void testActivity(View view) {
        startActivity(new Intent(context, TestActivity.class));
    }
}