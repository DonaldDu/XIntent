package com.dhy.xintent

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.text.SimpleDateFormat

@RunWith(RobolectricTestRunner::class)
class XIntentTest {

    @Test
    fun testReadSerializable() {
        val a = true
        val b = "4564654"
        val intent = XIntent(a, b)
        val f = SimpleDateFormat("yyyy-MM-dd\\T HH:mm:ss")
        val d = f.parse("2019-09-04T20:21:32")
        println(f.format(d))
    }
}
