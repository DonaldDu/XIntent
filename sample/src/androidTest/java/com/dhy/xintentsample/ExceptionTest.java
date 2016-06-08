package com.dhy.xintentsample;

import android.content.Context;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

/**
 * Created by donald on 2016/2/24.
 */
public class ExceptionTest extends ActivityUnitTestCase<XIntentSampleActivity> {
	public ExceptionTest() {
		super(XIntentSampleActivity.class);
	}

	Context context;
	TextView textView;

//	@Override
//	protected void setUp() throws Exception {
//		super.setUp();
//		context = getInstrumentation().getTargetContext();
//		textView = new TextView(context);
//		textView.setContentDescription("465456456");
//	}

	public void testMe() {
		String input = "11";
		long size = 10000 * 10000, costA, costB;
		long start = System.currentTimeMillis();
		for (long i = 0; i < size; i++) {
			if (input != null) {
				input.equals("");
			} else throw new IllegalArgumentException("");
		}
		System.out.println(costA = System.currentTimeMillis() - start);
		start = System.currentTimeMillis();
		for (long i = 0; i < size; i++) {
			try {
				input.equals("");
			} catch (Exception ignored) {
				throw new IllegalArgumentException("");
			}
		}
		System.out.println(costB = System.currentTimeMillis() - start);
		assertEquals(costA, costB);
	}

	void otestException() {
		long size = 1000 * 10000;//* 10000
		long start = System.currentTimeMillis(), costa, costb;
		for (long i = 0; i < size; i++) {
			CharSequence description = textView.getContentDescription();
			if (description != null) {
				textView.setText(description.toString());
			} else throw new IllegalArgumentException("");
		}
		System.out.println(costa = System.currentTimeMillis() - start);
		start = System.currentTimeMillis();
		for (long i = 0; i < size; i++) {
			try {
				textView.setText(textView.getContentDescription().toString());
			} catch (Exception ignored) {
				throw new IllegalArgumentException("");
			}
		}
		System.out.println(costb = System.currentTimeMillis() - start);
		assertEquals(costa, costb);
	}
}
