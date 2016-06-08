package com.dhy.xintent;

import android.content.Context;

import com.dhy.xintent.util.BaseActivityUnitTestCase;

public class ICommonSetttingsTest extends BaseActivityUnitTestCase {
	public void testSettings() {
		LoginResponse r;

		r = new LoginResponse(context);
		r.save();

		r = new LoginResponse(context);
		r.load();
		assertNotNull(r.testContext);
		assertEquals(0, r.sum());

		r = new LoginResponse(context);
		r.name = 1;
		r.pwd = 2;
		r.save();

		r = new LoginResponse(context);
		r.load();
		assertEquals(3, r.sum());
	}

	private class LoginResponse extends ICommonSettings<LoginResponse> {
		int name, pwd;
		int tel;
		transient Context testContext;

		public LoginResponse(Context context) {
			super(context);
			testContext = context;
		}

		public int sum() {
			return name + pwd;
		}
	}
}
