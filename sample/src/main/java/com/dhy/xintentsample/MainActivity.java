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
		normalWriteMethod(null);
	}

	public static final String KEY_MSG_a = "key_msg";
	public static final String KEY_MSG_b = "key_msg";
	public static final String KEY_MSG_c = "key_msg";
	public static final String KEY_MSG_d = "key_msg";

	public void normalWriteMethod(Data data) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(KEY_MSG_a, "");
		intent.putExtra(KEY_MSG_b, false);
		intent.putExtra(KEY_MSG_c, 1);
		intent.putExtra(KEY_MSG_d, data);
		startActivity(intent);
	}

	public void XIntentStartMethod(Data data) {
		startActivity(new XIntent(this, MainActivity.class, "", false, 1, data));
	}
}
