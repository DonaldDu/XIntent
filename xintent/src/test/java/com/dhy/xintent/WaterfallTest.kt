package com.dhy.xintent

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.lang.ref.WeakReference

class WaterfallTest {
    @Test
    fun testMe() {
        Waterfall.flow {
            next()
        }.flow {
            val data: String? = getPreResult()
            it.next(data ?: "")
        }.onEnd {
            assertEquals(getPreResult(), "")
        }
    }

    @Test
    fun test2() {
        var i = 0
        Waterfall.flow {
            testOrderOfNext(++i)
        }.flow {
            testOrderOfNext(++i)
        }.flow {
            testOrderOfNext(++i)
        }.flow {
            testOrderOfNext(++i)
        }.flow {
            testOrderOfNext(++i)
        }.flow {
            testOrderOfNext(++i)
        }.flow {
            testOrderOfNext(++i)
        }.flow {
            testOrderOfNext(++i)
        }
    }

    private fun Flow.testOrderOfNext(step: Int) {
        println("step:$step")
        if (step > 1) {
            val i: Int = getPreResult()
            assertEquals(step - 1, i)
        }
        next(step)
    }

    @Test
    fun testGC() {
        val waterfall = WeakReference(Waterfall.flow {
            println("step:1")
            next()
        })
        waterfall.get()!!.flow {
            println("step:2")
            next()
        }.flow {
            println("step:3")
            next()
        }
        System.gc()
        assertNull(waterfall.get())
    }

    @Test
    fun testInline() {

    }

    @Test
    fun testOnEnd() {
        Waterfall.flow {
            println("step:1")
            end(1)
        }.flow {
            println("step:2")
            next(2)
        }.onEnd {
            println("step:end")
        }
    }
}