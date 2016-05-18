package com.dhy.xintent;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class TestImpload extends TestCase {
	public void testImpload() {
		List<Data> list = new ArrayList<>();
		list.add(new Data());
		assertEquals(value, Impload.getString(list));
		list.add(new Data());
		assertEquals(value + "," + value, Impload.getString(list));
	}

	static final String value = "1";

	class Data implements Impload.Imploadable {

		@Override
		public String getImoploadString() {
			return value;
		}
	}
}
