package com.dhy.xintent

import kotlin.reflect.KClass

interface Flow {
    fun getPreResult(): Any?
    fun getResult(step: Int): Any?
    fun <T : Any> getResult(cls: KClass<T>): T?
    fun next(result: Any? = null, onUiThread: Boolean = false)
    fun error(result: Any? = null, onUiThread: Boolean = false)
}