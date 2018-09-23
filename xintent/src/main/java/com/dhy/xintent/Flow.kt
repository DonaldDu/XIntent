package com.dhy.xintent

interface Flow {
    fun <T : Any> getPreResult(): T
    fun <T : Any> getResult(step: Int): T
    fun next(result: Any? = null, onUiThread: Boolean = false)
    fun error(result: Any? = null, onUiThread: Boolean = false)
}