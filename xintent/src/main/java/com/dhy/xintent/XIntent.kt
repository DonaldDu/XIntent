package com.dhy.xintent

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

import java.io.Serializable
import kotlin.reflect.KClass

/**
 * An easy way to handle intent extra
 */
@SuppressLint("ParcelCreator")
class XIntent : Intent {

    constructor(bundle: Bundle) : super() {
        replaceExtras(bundle)
    }

    constructor(context: Context, cls: KClass<out Activity>, vararg serializable: Any?) : super(context, cls.java) {
        if (serializable.isNotEmpty()) {
            val datas: Array<Serializable?> = arrayOfNulls(serializable.size)
            for ((i, d) in serializable.withIndex()) {
                datas[i] = d as Serializable?
            }
            putSerializableExtra(this, *datas)
        }
    }

    constructor(context: Context, cls: Class<out Activity>, vararg serializable: Serializable?) : super(context, cls) {
        putSerializableExtra(this, *serializable)
    }

    companion object {
        @Suppress("MemberVisibilityCanBePrivate")
        val KEY_EXTRA: String = XIntent::class.java.name

        @JvmStatic
        fun startActivity(context: Context, cls: Class<out Activity>, vararg serializable: Serializable?) {
            context.startActivity(XIntent(context, cls, *serializable))
        }

        fun startActivity(context: Context, cls: KClass<out Activity>, vararg serializable: Serializable?) {
            context.startActivity(XIntent(context, cls, *serializable))
        }

        @JvmStatic
        fun putSerializableExtra(intent: Intent, vararg serializable: Serializable?): Intent {
            if (serializable.isNotEmpty()) intent.putExtra(KEY_EXTRA, serializable)
            return intent
        }

        /**
         * use [.readSerializableExtra] or other methods to get data out
         */
        @JvmStatic
        fun putSerializableExtra(bundle: Bundle, vararg serializable: Serializable?) {
            if (serializable.isNotEmpty()) bundle.putSerializable(KEY_EXTRA, serializable)
        }

        @JvmStatic
        fun putSerializableExtra(activity: Activity, vararg serializable: Serializable?) {
            putSerializableExtra(activity.intent, *serializable)
        }

        @JvmStatic
        fun <T> readSerializableExtra(activity: Activity, cls: Class<T>, defaultValue: T?): T? {
            return readSerializableExtra(activity.intent, cls, defaultValue)
        }

        @JvmStatic
        fun <T> readSerializableExtra(activity: Activity, cls: Class<T>): T? {
            return readSerializableExtra(activity.intent, cls)
        }

        @JvmStatic
        fun readSerializableExtra(activity: Activity): Serializable? {
            return readSerializableExtra(activity.intent)
        }

        @JvmStatic
        fun <T> readSerializableExtra(intent: Intent, cls: Class<T>): T? {
            return readSerializableExtra(intent, cls, null)
        }

        @JvmStatic
        fun readSerializableExtra(intent: Intent): Serializable? {
            return intent.getSerializableExtra(KEY_EXTRA)
        }

        @JvmStatic
        fun readSerializableExtra(activity: Activity, index: Int): Serializable? {
            return readSerializableExtra(activity.intent, index)
        }

        @JvmStatic
        fun readSerializableExtra(intent: Intent, index: Int): Serializable? {
            return readSerializableExtra(intent, Serializable::class.java, index, null)
        }

        @JvmStatic
        inline fun <reified T> readSerializableExtra(activity: Activity, defaultValue: T?): T? {
            return readSerializableExtra(activity, T::class.java, defaultValue)
        }

        @JvmStatic
        fun <T> readSerializableExtra(activity: Activity, cls: Class<T>, index: Int, defaultValue: T?): T? {
            return readSerializableExtra(activity.intent, cls, index, defaultValue)
        }

        @JvmStatic
        fun <T> readSerializableExtra(intent: Intent, cls: Class<T>, defaultValue: T?): T? {
            val serializable = readSerializableExtra(intent)
            return if (serializable is Array<*>) {
                val find = serializable.find { cls.isInstance(it) }
                if (find != null) cls.cast(find) else defaultValue
            } else defaultValue
        }

        @JvmStatic
        fun <T> readSerializableExtraList(activity: Activity, cls: Class<T>): List<T>? {
            return readSerializableExtraList(activity.intent, cls)
        }

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T> readSerializableExtraList(intent: Intent, cls: Class<T>): List<T>? {
            val serializable = readSerializableExtra(intent)
            if (serializable is Array<*>) {
                return serializable.find {
                    if (it is List<*>) {
                        it.find { i -> cls.isInstance(i) } != null
                    } else false
                } as List<T>?
            }
            return null
        }

        @JvmStatic
        fun <T> readSerializableExtra(intent: Intent, cls: Class<T>, index: Int, defaultValue: T?): T? {
            val serializable = readSerializableExtra(intent)
            if (serializable is Array<*>) {
                if (index < serializable.size) return cls.cast(serializable[index])
            }
            return defaultValue
        }
    }
}

inline fun <reified T : Serializable> Activity.readExtra(defaultValue: T? = null): T? {
    return XIntent.readSerializableExtra(this, T::class.java, defaultValue)
}

inline fun <reified T : Serializable> Activity.readExtraOfList(): List<T>? {
    return XIntent.readSerializableExtraList(this, T::class.java)
}

@Deprecated("use readExtraOfList", replaceWith = ReplaceWith("readExtraOfList"))
inline fun <reified T : Serializable> Activity.readListExtra(): List<T>? {
    return XIntent.readSerializableExtraList(this, T::class.java)
}

fun Activity.startActivity(cls: KClass<out Activity>, vararg serializable: Any?) {
    startActivity(XIntent(this, cls, *serializable))
}