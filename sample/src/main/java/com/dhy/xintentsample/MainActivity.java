package com.dhy.xintentsample;

import android.app.Activity;
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
	}
}
