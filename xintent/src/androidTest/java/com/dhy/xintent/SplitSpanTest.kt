package com.dhy.xintent

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dhy.xintent.style.SplitSpan
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplitSpanTest {
    @Test
    fun getSpitedText() {
        for (i in 0 until 15) {
            val s = "1".repeat(i)
            println("getSpitedText>$s")
            SplitSpan.getSpitedText(s, intArrayOf(3, 4, 4), " ")
        }
    }
}