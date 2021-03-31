package com.dhy.xintent

import android.os.Bundle
import com.dhy.xintent.data.DataNotSerializable
import com.dhy.xintent.data.DataSerializable
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IntentWrapperTest {
    @Test
    fun testIntent() {
        XIntent.with().apply {
            val ds = DataSerializable()
            ds.anInt = 1
            putSerializableExtra(1, false, "1", listOf("1"), ds)
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

            val d: DataSerializable? = readExtra()
            Assert.assertEquals(1, d?.anInt)
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

    @Test(expected = ClassCastException::class)
    fun testNotSerializable() {
        XIntent.with().putSerializableExtra(DataNotSerializable())
    }
}