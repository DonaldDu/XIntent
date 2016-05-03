package com.dhy.xintent;

import android.content.Context;

import com.dhy.xintent.util.BaseActivityUnitTestCase;
import com.google.gson.annotations.Expose;

public class TestICommonSetttings extends BaseActivityUnitTestCase {
	public void test() {
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
		@Expose
		int name, pwd;
		@Expose
		int tel;
		Context testContext;

		public LoginResponse(Context context) {
			super(context, Expose.class);
//			tel = new Date();
			testContext = context;
		}

		@Override
		public LoginResponse load() {
			return load(this, LoginResponse.class, new LoginResponse(context));
		}

		public int sum() {
			return name + pwd;
		}
	}
}
