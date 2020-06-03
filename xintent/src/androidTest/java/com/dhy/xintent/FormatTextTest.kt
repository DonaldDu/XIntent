package com.dhy.xintent

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dhy.xintent.util.BaseActivityUnitTestCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FormatTextTest : BaseActivityUnitTestCase() {
    private val tv by lazy { TextView(context) }

    @Test
    fun testNumber() {
        tv.contentDescription = "%d"
        tv.formatText(1)
        Assert.assertEquals(1, tv.text.toString().toInt())
    }
}