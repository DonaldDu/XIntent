package com.dhy.xintent;

import android.content.Intent;

import com.dhy.xintent.util.BaseActivityUnitTestCase;
import com.dhy.xintent.util.TestMainActivity;

public class XIntentTest extends BaseActivityUnitTestCase {

	final Boolean a = true;
	final String b = "4564654";

	@Override
	protected Intent getStartIntent() {
		return new XIntent(context, TestMainActivity.class, a, b);
	}

	public void testReadSerializable() {
		assertEquals(XIntent.readSerializableExtra(getActivity(), Boolean.class, null), a);
		assertEquals(XIntent.readSerializableExtra(getActivity(), Boolean.class, 0, null), a);

		assertEquals(XIntent.readSerializableExtra(getActivity(), String.class, null), b);
		assertEquals(XIntent.readSerializableExtra(getActivity(), String.class, 1, null), b);
	}
}
