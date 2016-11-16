package com.dhy.xintent;

import com.google.gson.Gson;

import junit.framework.TestCase;

public class GsonTest extends TestCase {
    public void testBaseType() {
        String s = "554546465465";
        Gson gson = new Gson();
        String out = gson.fromJson(s, String.class);
        assertEquals(s, out);

        s = "5";
        assertEquals(Integer.parseInt(s), (int) gson.fromJson(s, Integer.class));
    }
}
