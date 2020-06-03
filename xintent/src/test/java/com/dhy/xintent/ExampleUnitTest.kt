package com.dhy.xintent

import org.junit.Assert
import org.junit.Test

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2.toLong())
    }

    @Test
    fun testNull() {
        val num: Double? = null
        Assert.assertEquals("null", String.format("%.4f", num))
        Assert.assertEquals("nu", String.format("%.2f", num))
    }
}