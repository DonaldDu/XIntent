package com.dhy.xintentsample;

import android.app.Activity;
import android.os.Bundle;

import com.dhy.xintent.XIntent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		XIntent.putSerializableExtra(this,"");
	}
}
