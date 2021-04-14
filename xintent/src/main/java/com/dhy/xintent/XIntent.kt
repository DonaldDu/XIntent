package com.dhy.xintent

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.io.Serializable
import java.lang.reflect.Field
import kotlin.reflect.KClass

/**
 * An easy way to handle intent extra
 */
@SuppressLint("ParcelCreator")
class XIntent : Intent {

    constructor(bundle: Bundle) : super() {
        directExtras = bundle
    }

    constructor(context: Context, cls: Class<out Activity>, vararg serializable: Any?) : super(context, cls) {
        with(this).putSerializableExtra(*serializable)
    }

    constructor(context: Context, cls: KClass<out Activity>, vararg serializable: Any?) : this(context, cls.java, *serializable)

    companion object {
        @Suppress("MemberVisibilityCanBePrivate")
        val KEY_EXTRA: String = XIntent::class.java.name

        private val mExtrasFiled: Field by lazy {
            val f = Intent::class.java.getDeclaredField("mExtras")
            f.isAccessible = true
            f
        }

        private var Intent.directExtras: Bundle?
            get() {
                return mExtrasFiled.get(this) as Bundle?
            }
            set(value) {
                mExtrasFiled.set(this, value)
            }

        @JvmStatic
        fun with(container: Intent? = null): Intent {
            return container ?: Intent()
        }

        @JvmStatic
        fun with(container: Bundle): Intent {
            return XIntent(container)
        }

        @JvmStatic
        fun with(container: Activity): Intent {
            return container.intent
        }

        @JvmStatic
        fun startActivity(context: Context, cls: Class<out Activity>, vararg serializable: Any?) {
            context.startActivity(XIntent(context, cls, *serializable))
        }

        fun startActivity(context: Context, cls: KClass<out Activity>, vararg serializable: Any?) {
            context.startActivity(XIntent(context, cls, *serializable))
        }

        //region Deprecated just compat old methods
        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(intent).putSerializableExtra(*serializable)"))
        @JvmStatic
        fun putSerializableExtra(intent: Intent, vararg serializable: Serializable?): Intent {
            if (serializable.isNotEmpty()) intent.putExtra(KEY_EXTRA, serializable)
            return intent
        }

        /**
         * use [.readSerializableExtra] or other methods to get data out
         */
        @JvmStatic
        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(bundle).putSerializableExtra(*serializable)"))
        fun putSerializableExtra(bundle: Bundle, vararg serializable: Serializable?) {
            if (serializable.isNotEmpty()) bundle.putSerializable(KEY_EXTRA, serializable)
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(activity).putSerializableExtra(*serializable)"))
        @JvmStatic
        fun putSerializableExtra(activity: Activity, vararg serializable: Serializable?) {
            putSerializableExtra(activity.intent, *serializable)
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(activity).readExtra(cls, defaultValue)"))
        @JvmStatic
        fun <T> readSerializableExtra(activity: Activity, cls: Class<T>, defaultValue: T?): T? {
            return readSerializableExtra(activity.intent, cls, defaultValue)
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(activity).readExtra(cls)"))
        @JvmStatic
        fun <T> readSerializableExtra(activity: Activity, cls: Class<T>): T? {
            return readSerializableExtra(activity.intent, cls)
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(activity).getSerializableExtra()"))
        @JvmStatic
        fun readSerializableExtra(activity: Activity): Serializable? {
            return readSerializableExtra(activity.intent)
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(intent).readExtra(cls)"))
        @JvmStatic
        fun <T> readSerializableExtra(intent: Intent, cls: Class<T>): T? {
            return readSerializableExtra(intent, cls, null)
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(intent).getSerializableExtra()"))
        @JvmStatic
        fun readSerializableExtra(intent: Intent): Serializable? {
            return intent.getSerializableExtra(KEY_EXTRA)
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(activity).readExtra(Serializable::class.java, index, null)"))
        @JvmStatic
        fun readSerializableExtra(activity: Activity, index: Int): Serializable? {
            return readSerializableExtra(activity.intent, index)
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(intent).readExtra(Serializable::class.java, index, null)"))
        @JvmStatic
        fun readSerializableExtra(intent: Intent, index: Int): Serializable? {
            return readSerializableExtra(intent, Serializable::class.java, index, null)
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(activity).readExtra(defaultValue)"))
        @JvmStatic
        inline fun <reified T> readSerializableExtra(activity: Activity, defaultValue: T?): T? {
            return readSerializableExtra(activity, T::class.java, defaultValue)
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(activity).readExtra(cls, index, defaultValue)"))
        @JvmStatic
        fun <T> readSerializableExtra(activity: Activity, cls: Class<T>, index: Int, defaultValue: T?): T? {
            return readSerializableExtra(activity.intent, cls, index, defaultValue)
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(intent).readExtra(cls, defaultValue)"))
        @JvmStatic
        fun <T> readSerializableExtra(intent: Intent, cls: Class<T>, defaultValue: T?): T? {
            val serializable = readSerializableExtra(intent)
            return if (serializable is Array<*>) {
                val find = serializable.find { cls.isInstance(it) }
                if (find != null) cls.cast(find) else defaultValue
            } else defaultValue
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(activity).readExtraList(cls)"))
        @JvmStatic
        fun <T> readSerializableExtraList(activity: Activity, cls: Class<T>): List<T> {
            return readSerializableExtraList(activity.intent, cls)
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(intent).readExtraList(cls)"))
        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T> readSerializableExtraList(intent: Intent, cls: Class<T>): List<T> {
            val array = readSerializableExtra(intent) as? Array<*>

            @Suppress("UNCHECKED_CAST")
            val data = array?.find { it is List<*> && it.any { i -> cls.isInstance(i) } } as List<T>?
            return data ?: emptyList()
        }

        @Deprecated("use XIntent.with()", replaceWith = ReplaceWith("XIntent.with(intent).readExtra(cls, index, defaultValue)"))
        @JvmStatic
        fun <T> readSerializableExtra(intent: Intent, cls: Class<T>, index: Int, defaultValue: T?): T? {
            val array = readSerializableExtra(intent) as? Array<*>
            return if (array != null && index < array.size) cls.cast(array[index]) else defaultValue
        }
//endregion
    }
}

//region Deprecated
@Deprecated("use readExtraList", replaceWith = ReplaceWith("readExtraList()"))
inline fun <reified T : Serializable> Activity.readExtraOfList(): List<T> {
    return XIntent.readSerializableExtraList(this, T::class.java)
}

@Deprecated("use readExtraOfList", replaceWith = ReplaceWith("readExtraList()"))
inline fun <reified T : Serializable> Activity.readListExtra(): List<T> {
    return XIntent.readSerializableExtraList(this, T::class.java)
}

@Deprecated("use XIntent.startActivity", replaceWith = ReplaceWith("XIntent.startActivity()"))
fun Activity.startActivity(cls: KClass<out Activity>, vararg serializable: Any?) {
    startActivity(XIntent(this, cls, *serializable))
}
//endregion