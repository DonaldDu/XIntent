package com.dhy.xintentsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dhy.xintent.XCommon;
import com.dhy.xintent.XIntent;

public class MainActivity extends Activity {
	boolean init = false;
	int hash;
	String tag = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final String msg = XIntent.readSerializableExtra(this, String.class);
		XCommon.setTextWithFormat(this, R.id.textView, msg);
		final EditText editText = (EditText) findViewById(R.id.editText);
		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new XIntent(MainActivity.this, MainActivity.class, editText.getText().toString()));
			}
		});
		if (savedInstanceState != null) {
			Intent intent = new Intent().replaceExtras(savedInstanceState);
			hash = XIntent.readSerializableExtra(intent, Integer.class, -1);
		}
		Log.i(tag, "init " + init);
		Log.i(tag, "hash1 " + hash);
		init = true;
		hash = findViewById(R.id.textView).hashCode();
		Log.i(tag, "hash2 " + hash);
//		normalSetMethod(null, true, 1, null);
	}

	public static final String KEY_MSG_a = "key_msg_a";
	public static final String KEY_MSG_b = "key_msg_b";
	public static final String KEY_MSG_c = "key_msg_c";
	public static final String KEY_MSG_d = "key_msg_d";

	public void normalSetMethod(String a, boolean b, int c, Data d) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(KEY_MSG_a, a);
		intent.putExtra(KEY_MSG_b, b);
		intent.putExtra(KEY_MSG_c, c);
		intent.putExtra(KEY_MSG_d, d);
		startActivity(intent);
	}

	public void XIntentSetResult(String a, boolean b, int c, Data d) {
		Intent intent = new Intent();
		XIntent.putSerializableExtra(intent, a, b, c, d);
		setResult(RESULT_OK, intent);
	}

	public void XIntentSetMethod(String a, boolean b, int c, Data d) {
		startActivity(new XIntent(this, MainActivity.class, a, b, c, d));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		XIntent.putSerializableExtra(outState, "+", hash);
		super.onSaveInstanceState(outState);
		Log.i(tag, "======================= onSaveInstanceState =======================");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Intent intent = new Intent().replaceExtras(savedInstanceState);
		String a = XIntent.readSerializableExtra(intent, String.class, "");
		Integer b = XIntent.readSerializableExtra(intent, Integer.class, 0);
		Log.i(tag, "======================= onRestoreInstanceState =======================" + a + b);
	}
}
