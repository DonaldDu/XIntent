package com.dhy.xintentsample;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import com.dhy.xintent.XCommon;

import java.io.Serializable;

/**
 * Created by donald on 2016/2/24.
 */
public class SetTextUtilTest extends ActivityUnitTestCase<MainActivity> {
	public SetTextUtilTest() {
		super(MainActivity.class);
	}

	Intent launchIntent;
	final boolean a = true;
	final String b = "4564654";
	Context context;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		context = getInstrumentation().getTargetContext();
		launchIntent = new Intent(context, MainActivity.class);
		launchIntent.putExtra(MainActivity.class.getName(), new Serializable[]{a, b});
		startActivity(launchIntent, null, null);
	}

	TextView textView;

	public void testSetText() {
		textView = new TextView(context);
		check("price%d", 1, "price1");
		check("price%s", 1, "price1");
		check("price%s", null, "price");
		check("price%1$.1f,price%2$.2f", new Object[]{1.1, 2.2}, "price1.1,price2.20");
	}

	void check(String format, Object input, String output) {
		textView.setContentDescription(format);
		XCommon.setTextWithFormat(textView, input);
		assertEquals(output, textView.getText().toString());
	}
}
