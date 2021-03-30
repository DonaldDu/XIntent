package com.dhy.xintent

import android.os.Bundle
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntentWrapperTest {
    @Test
    fun testIntent() {
        XIntent.with().apply {
            putSerializableExtra(1, false, "1", listOf("1"))
            val i: Int? = readExtra()
            Assert.assertEquals(1, i)

            val b: Boolean? = readExtra()
            Assert.assertEquals(false, b)

            val s: String? = readExtra()
            Assert.assertEquals("1", s)

            val list: List<String> = readExtraList()
            Assert.assertEquals(listOf("1"), list)

            val listByIndex: List<String>? = readExtra(3, null)
            Assert.assertEquals(listOf("1"), listByIndex)
        }
    }

    @Test
    fun testBundleInvertedOrder() {
        val bundle = Bundle()
        XIntent.with(bundle).apply {
            putSerializableExtra(1, false, "1", listOf("1"))
        }

        XIntent.with(bundle).apply {
            val list: List<String> = readExtraList()
            Assert.assertEquals(listOf("1"), list)

            val s: String? = readExtra()
            Assert.assertEquals("1", s)

            val b: Boolean? = readExtra()
            Assert.assertEquals(false, b)

            val i: Int? = readExtra()
            Assert.assertEquals(1, i)
        }
    }

    @Test
    fun testDefaultOnly() {
        XIntent.with().apply {
            val i: Int? = readExtra(defaultValue = 1)
            Assert.assertEquals(1, i)

            val b: Boolean? = readExtra(false)
            Assert.assertEquals(false, b)

            val s: String? = readExtra("1")
            Assert.assertEquals("1", s)

            val list: List<String>? = readExtra(0, null)
            Assert.assertEquals(null, list)
        }
    }
}