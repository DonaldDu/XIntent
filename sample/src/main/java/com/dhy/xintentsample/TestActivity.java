package com.dhy.xintentsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dhy.xintent.XIntent;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by donald on 2016/2/5.
 */
public class TestActivity extends Activity {
	public static final String KEY_MSG_a = "key_msg_a";
	public static final String KEY_MSG_b = "key_msg_b";
	public static final String KEY_MSG_c = "key_msg_c";
	public static final String KEY_MSG_d = "key_msg_d";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		normalGetMethod();
		XIntentGetMethod();
	}

	void normalGetMethod() {
		Intent intent = getIntent();
		String a = intent.getStringExtra(KEY_MSG_a);
		boolean b = intent.getBooleanExtra(KEY_MSG_b, false);
		int c = intent.getIntExtra(KEY_MSG_c, 1);
		Date d = (Date) intent.getSerializableExtra(KEY_MSG_d);
	}

	void XIntentGetMethod() {
		String a = XIntent.readSerializableExtra(this, String.class, "");
		boolean b = XIntent.readSerializableExtra(this, Boolean.class, false);
		int c = XIntent.readSerializableExtra(this, Integer.class, 1);
		Data d = XIntent.readSerializableExtra(this, Data.class);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String a = XIntent.readSerializableExtra(data, String.class, "");
		boolean b = XIntent.readSerializableExtra(data, Boolean.class, false);
		int c = XIntent.readSerializableExtra(data, Integer.class, 1);
		Data d = XIntent.readSerializableExtra(data, Data.class);
	}

	class Data implements Serializable {

	}
}
