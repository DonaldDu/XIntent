package com.dhy.xintent.util;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;

public class BaseActivityUnitTestCase extends ActivityUnitTestCase<TestMainActivity> {
	public BaseActivityUnitTestCase() {
		super(TestMainActivity.class);
	}

	protected Context context;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		context = getInstrumentation().getTargetContext();
		startActivity(getStartIntent(), null, null);
	}

	protected Intent getStartIntent() {
		return new Intent(context, TestMainActivity.class);
	}
}
