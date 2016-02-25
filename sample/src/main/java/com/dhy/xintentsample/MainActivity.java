package com.dhy.xintentsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dhy.xintent.XIntent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView v = (TextView) findViewById(R.id.textView);
		final String msg = XIntent.readSerializableExtra(this, String.class);
		v.setText(msg);
		final EditText editText = (EditText) findViewById(R.id.editText);
		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new XIntent(MainActivity.this, MainActivity.class, editText.getText().toString()));
			}
		});
		normalSetMethod(null, true, 1, null);
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
}
