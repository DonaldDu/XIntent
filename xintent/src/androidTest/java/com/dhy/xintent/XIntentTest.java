package com.dhy.xintent;

import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;

import com.dhy.xintent.util.BaseActivityUnitTestCase;
import com.dhy.xintent.util.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class XIntentTest extends BaseActivityUnitTestCase {

    final Boolean a = true;
    final String b = "4564654";

    @Test
    public void testReadSerializable() {
        Intent intent = new XIntent(context, MainActivity.class, a, b);
        assertEquals(XIntent.readSerializableExtra(intent, Boolean.class, null), a);
        assertEquals(XIntent.readSerializableExtra(intent, Boolean.class, 0, null), a);

        assertEquals(XIntent.readSerializableExtra(intent, String.class, null), b);
        assertEquals(XIntent.readSerializableExtra(intent, String.class, 1, null), b);
    }
}
