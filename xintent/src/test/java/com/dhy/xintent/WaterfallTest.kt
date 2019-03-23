package com.dhy.xintent

import org.junit.Assert.assertEquals
import org.junit.Test

class WaterfallTest {
    @Test
    fun testMe() {
        Waterfall().flow { f ->
            next()
        }.flow {
            val data: String? = this.getPreResult()
            it.next(data ?: "")
        }.onEnd {
            assertEquals(it.getPreResult(), "")
        }
    }

    @Test
    fun test2() {
        Waterfall().flow {
            println("step:1")
            next()
        }.flow {
            println("step:2")
            next()
        }.flow {
            println("step:3")
            next()
        }.flow {
            println("step:4")
            next()
        }.flow {
            println("step:5")
            next()
        }.flow {
            println("step:6")
            next()
        }.flow { f ->
            println("step:7")
            f.next()
        }.flow {
            println("step:8")
            this.next()
        }
    }
}