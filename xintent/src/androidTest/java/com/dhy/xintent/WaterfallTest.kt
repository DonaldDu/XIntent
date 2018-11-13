package com.dhy.xintent

import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WaterfallTest {
    @Test
    fun testMe() {
        Waterfall().flow {
            it.next()
        }.flow {
            val data: String? = it.getPreResult()
            it.next(data?:"")
        }.onEnd {
            assertEquals(it.getPreResult(),"")
        }.start()
    }
}