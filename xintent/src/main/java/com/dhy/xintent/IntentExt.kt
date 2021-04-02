package com.dhy.xintent

import android.app.Activity
import android.content.Intent
import java.io.Serializable

fun Intent.putSerializableExtra(vararg serializable: Any?): Intent {
    if (serializable.isNotEmpty()) {
        val array = Array(serializable.size) { serializable[it] as Serializable? }
        putExtra(XIntent.KEY_EXTRA, array as Serializable)
    }
    return this
}

fun Intent.getSerializableExtra(): Array<Serializable?>? {
    @Suppress("UNCHECKED_CAST")
    return getSerializableExtra(XIntent.KEY_EXTRA) as Array<Serializable?>?
}

fun <T : Serializable> Intent.readExtra(cls: Class<T>, defaultValue: T? = null): T? {
    val array = getSerializableExtra()
    val find = array?.find { cls.isInstance(it) }
    return if (find != null) cls.cast(find) else defaultValue
}

fun <T : Serializable> Intent.readExtra(cls: Class<T>, index: Int, defaultValue: T? = null): T? {
    val array = getSerializableExtra()
    return if (array != null && index < array.size) cls.cast(array[index]) else defaultValue
}

fun <T : Serializable> Intent.readExtraList(cls: Class<T>): List<T> {
    val array = getSerializableExtra()

    @Suppress("UNCHECKED_CAST")
    val data = array?.find { it is List<*> && it.any { i -> cls.isInstance(i) } } as List<T>?
    return data ?: emptyList()
}

inline fun <reified T : Serializable> Intent.readExtra(defaultValue: T? = null): T? {
    return readExtra(T::class.java, defaultValue)
}

inline fun <reified T : Serializable> Intent.readExtra(index: Int, defaultValue: T? = null): T? {
    return readExtra(T::class.java, index, defaultValue)
}

inline fun <reified T : Serializable> Intent.readExtraList(): List<T> {
    return readExtraList(T::class.java)
}

inline fun <reified T : Serializable> Activity.readExtra(defaultValue: T? = null): T? {
    return XIntent.with(this).readExtra(defaultValue)
}

inline fun <reified T : Serializable> Activity.readExtra(index: Int, defaultValue: T? = null): T? {
    return XIntent.with(this).readExtra(index, defaultValue)
}

inline fun <reified T : Serializable> Activity.readExtraList(): List<T> {
    return XIntent.with(this).readExtraList()
}