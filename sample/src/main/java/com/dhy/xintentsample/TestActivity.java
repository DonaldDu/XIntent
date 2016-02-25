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
	public static final String KEY_MSG_a = "key_msg";
	public static final String KEY_MSG_b = "key_msg";
	public static final String KEY_MSG_c = "key_msg";
	public static final String KEY_MSG_d = "key_msg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		normalGetMethod();
		XIntentGetMethod();
	}

	void normalGetMethod() {
		Intent intent = getIntent();
		String msg = intent.getStringExtra(KEY_MSG_a);
		boolean b = intent.getBooleanExtra(KEY_MSG_b, false);
		int a = intent.getIntExtra(KEY_MSG_c, 1);
		Date data = (Date) intent.getSerializableExtra(KEY_MSG_d);
	}

	void XIntentGetMethod() {
		XIntent.readSerializableExtra(this,String.class);
		String msg = XIntent.readSerializableExtra(this, String.class, "");
		boolean b = XIntent.readSerializableExtra(this, Boolean.class, false);
		int a = XIntent.readSerializableExtra(this, Integer.class, 1);
		Data data = XIntent.readSerializableExtra(this, Data.class);
	}

	class Data implements Serializable {

	}
}
