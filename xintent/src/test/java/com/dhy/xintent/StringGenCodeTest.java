package com.dhy.xintent;

import junit.framework.TestCase;

public class StringGenCodeTest extends TestCase {
    public void testAndChar() {
        String f = "h&m%1$s";
        String r = String.format(f, "?");
        assertEquals("h&m?", r);
    }
}
