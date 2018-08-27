package com.dhy.xintent

interface Flow {
    fun getPreResult(): Any?
    fun getResult(step: Int): Any?
    fun next(result: Any? = null, onUiThread: Boolean = false)
    fun error(result: Any? = null, onUiThread: Boolean = false)
}