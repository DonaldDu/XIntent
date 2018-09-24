package com.dhy.xintent

import kotlin.reflect.KClass

interface Flow {
    fun <T : Any> getPreResult(): T
    fun <T : Any> getResult(step: Int): T
    fun <T : Any> getResult(cls: KClass<T>): T?
    fun next(result: Any? = null, onUiThread: Boolean = false)
    fun error(result: Any? = null, onUiThread: Boolean = false)
}