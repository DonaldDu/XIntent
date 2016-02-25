package com.dhy.xintentsample;

import junit.framework.TestCase;

/**
 * Created by donald on 2016/2/24.
 */
public class SimpleTest extends TestCase {
	public void testFormat() {
		check("price%1$.1f,price%2$.2f", new Object[]{1.1, 2.2}, "price1.1,price2.20");
	}

	void check(String format, Object[] input, String output) {
		String out = String.format(format, input);
		System.out.println(out);
		assertEquals(output, out);
	}
}
