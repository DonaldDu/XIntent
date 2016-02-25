package com.dhy.xintentsample;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.dhy.xintent.XIntent;

import java.io.Serializable;

/**
 * Created by donald on 2016/2/24.
 */
public class XIntentTest extends ActivityUnitTestCase<MainActivity> {
	public XIntentTest() {
		super(MainActivity.class);
	}

	Intent launchIntent;
	final boolean a = true;
	final String b = "4564654";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		launchIntent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
		launchIntent.putExtra(MainActivity.class.getName(), new Serializable[]{a, b});
		startActivity(launchIntent, null, null);
	}

	public void testReadSerializable() {
		assertTrue(XIntent.readSerializableExtra(getActivity(), Boolean.class, false));
		assertTrue(XIntent.readSerializableExtra(getActivity(), Boolean.class, 0, false));

		assertTrue(b.equals(XIntent.readSerializableExtra(getActivity(), String.class, null)));
		assertTrue(b.equals(XIntent.readSerializableExtra(getActivity(), String.class, 1, null)));
	}
}
