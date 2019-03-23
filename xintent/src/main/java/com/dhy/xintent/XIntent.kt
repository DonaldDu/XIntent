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
        putSerializableExtra(this, *serializable as Array<out Serializable?>)
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

        fun putSerializableExtra(intent: Intent, vararg serializable: Serializable?): Intent {
            if (serializable.isNotEmpty()) intent.putExtra(KEY_EXTRA, serializable)
            return intent
        }

        /**
         * use [.readSerializableExtra] or other methods to get data out
         */
        fun putSerializableExtra(bundle: Bundle, vararg serializable: Serializable?) {
            if (serializable.isNotEmpty()) bundle.putSerializable(KEY_EXTRA, serializable)
        }

        fun putSerializableExtra(activity: Activity, vararg serializable: Serializable?) {
            putSerializableExtra(activity.intent, *serializable)
        }

        fun <T> readSerializableExtra(activity: Activity, cls: Class<T>, defaultValue: T?): T? {
            return readSerializableExtra(activity.intent, cls, defaultValue)
        }

        fun <T> readSerializableExtra(activity: Activity, cls: Class<T>): T? {
            return readSerializableExtra(activity.intent, cls)
        }

        fun readSerializableExtra(activity: Activity): Serializable? {
            return readSerializableExtra(activity.intent)
        }

        fun <T> readSerializableExtra(intent: Intent, cls: Class<T>): T? {
            return readSerializableExtra(intent, cls, null)
        }

        fun readSerializableExtra(intent: Intent): Serializable? {
            return intent.getSerializableExtra(KEY_EXTRA)
        }

        fun readSerializableExtra(activity: Activity, index: Int): Serializable? {
            return readSerializableExtra(activity.intent, index)
        }

        fun readSerializableExtra(intent: Intent, index: Int): Serializable? {
            return readSerializableExtra(intent, Serializable::class.java, index, null)
        }

        inline fun <reified T> readSerializableExtra(activity: Activity, defaultValue: T?): T? {
            val cls = T::class.java
            return readSerializableExtra(activity, cls, defaultValue)
        }

        fun <T> readSerializableExtra(activity: Activity, cls: Class<T>, index: Int, defaultValue: T?): T? {
            return readSerializableExtra(activity.intent, cls, index, defaultValue)
        }

        fun <T> readSerializableExtra(intent: Intent, cls: Class<T>, defaultValue: T?): T? {
            val serializable = readSerializableExtra(intent)
            if (serializable is Array<*>) {
                for (d in serializable) {
                    if (cls.isInstance(d)) {
                        return cls.cast(d)
                    }
                }
            }
            return defaultValue
        }

        fun <T> readSerializableExtraList(activity: Activity, cls: Class<T>): List<T>? {
            return readSerializableExtraList(activity.intent, cls)
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> readSerializableExtraList(intent: Intent, cls: Class<T>): List<T>? {
            val serializable = readSerializableExtra(intent)
            if (serializable is Array<*>) {
                return serializable.filter { it is List<*> && it.isNotEmpty() }
                        .find {
                            val list = it as List<*>
                            list.find { i -> cls.isInstance(i) } != null
                        } as List<T>?
            }
            return null
        }

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

inline fun <reified T : Serializable> Activity.readListExtra(): List<T>? {
    return XIntent.readSerializableExtraList(this, T::class.java)
}

fun Activity.startActivity(cls: KClass<out Activity>, vararg serializable: Any?) {
    startActivity(XIntent(this, cls, *serializable))
}