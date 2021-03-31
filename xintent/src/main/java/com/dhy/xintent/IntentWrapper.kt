package com.dhy.xintent

import android.content.Intent
import java.io.Serializable

class IntentWrapper(val intent: Intent) {
    fun putSerializableExtra(vararg serializable: Any?) {
        if (serializable.isNotEmpty()) {
            val array = Array(serializable.size) { serializable[it] as Serializable? }
            intent.putExtra(XIntent.KEY_EXTRA, array as Serializable)
        }
    }

    fun getSerializableExtra(): Array<Serializable?>? {
        @Suppress("UNCHECKED_CAST")
        return intent.getSerializableExtra(XIntent.KEY_EXTRA) as Array<Serializable?>?
    }

    fun <T : Serializable> readExtra(cls: Class<T>, defaultValue: T? = null): T? {
        val array = getSerializableExtra()
        val find = array?.find { cls.isInstance(it) }
        return if (find != null) cls.cast(find) else defaultValue
    }

    fun <T : Serializable> readExtra(cls: Class<T>, index: Int, defaultValue: T? = null): T? {
        val array = getSerializableExtra()
        return if (array != null && index < array.size) cls.cast(array[index]) else defaultValue
    }

    fun <T : Serializable> readExtraList(cls: Class<T>): List<T> {
        val array = getSerializableExtra()

        @Suppress("UNCHECKED_CAST")
        val data = array?.find { it is List<*> && it.any { i -> cls.isInstance(i) } } as List<T>?
        return data ?: emptyList()
    }

    inline fun <reified T : Serializable> readExtra(defaultValue: T? = null): T? {
        return readExtra(T::class.java, defaultValue)
    }

    inline fun <reified T : Serializable> readExtra(index: Int, defaultValue: T? = null): T? {
        return readExtra(T::class.java, index, defaultValue)
    }

    inline fun <reified T : Serializable> readExtraList(): List<T> {
        return readExtraList(T::class.java)
    }
}