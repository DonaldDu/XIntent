package com.dhy.xintent;

import android.app.Activity;
import android.content.Intent;

import com.dhy.xintent.data.Data;
import com.dhy.xintent.util.MainActivity;

public class Sample extends Activity {
	//region 通常的方式如下
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

	void normalGetMethod() {
		Intent intent = getIntent();
		String a = intent.getStringExtra(KEY_MSG_a);
		boolean b = intent.getBooleanExtra(KEY_MSG_b, false);
		int c = intent.getIntExtra(KEY_MSG_c, 1);
		Data d = (Data) intent.getSerializableExtra(KEY_MSG_d);
	}
	//endregion

	//region 用XIntent(com.dhy.xintent.XIntent extends Intent)可以简化如下

	public void XIntentSetMethod(String a, boolean b, int c, Data d) {
		XIntent.Companion.startActivity(this, MainActivity.class, a, b, c, d);
	}

	public void XIntentSetMethod2(String a, boolean b, int c, Data d) {
		startActivity(new XIntent(this, MainActivity.class, a, b, c, d));
	}

	void XIntentGetMethod() {
		String a = XIntent.Companion.readSerializableExtra(this, String.class, "");
		boolean b = XIntent.Companion.readSerializableExtra(this, Boolean.class, false);
		int c = XIntent.Companion.readSerializableExtra(this, Integer.class, 1);
		Data d = XIntent.Companion.readSerializableExtra(this, Data.class);
	}

	//endregion

	//region 也可以直接操作Intent
	public void XIntentSetResult(String a, boolean b, int c, Data d) {
		Intent intent = new Intent();
		XIntent.Companion.putSerializableExtra(intent, a, b, c, d);
		setResult(RESULT_OK, intent);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		String a = XIntent.Companion.readSerializableExtra(data, String.class, "");
		boolean b = XIntent.Companion.readSerializableExtra(data, Boolean.class, false);
		int c = XIntent.Companion.readSerializableExtra(data, Integer.class, 1);
		Data d = XIntent.Companion.readSerializableExtra(data, Data.class);
	}
	//endregion
}
