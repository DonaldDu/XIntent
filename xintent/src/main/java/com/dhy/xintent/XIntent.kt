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
        fun with(container: Bundle): IntentWrapper {
            return with(XIntent(container))
        }

        @JvmStatic
        fun with(container: Intent? = null): IntentWrapper {
            return IntentWrapper(container ?: Intent())
        }

        @JvmStatic
        fun with(container: Activity): IntentWrapper {
            return with(container.intent)
        }

        @JvmStatic
        fun startActivity(context: Context, cls: Class<out Activity>, vararg serializable: Any?) {
            context.startActivity(XIntent(context, cls, *serializable))
        }

        fun startActivity(context: Context, cls: KClass<out Activity>, vararg serializable: Any?) {
            context.startActivity(XIntent(context, cls, *serializable))
        }

        //region Deprecated
        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun putSerializableExtra(intent: Intent, vararg serializable: Serializable?): Intent {
            with(intent).putSerializableExtra(*serializable)
            return intent
        }

        @Deprecated("use XIntent.with()")
        /**
         * use [.readSerializableExtra] or other methods to get data out
         */
        @JvmStatic
        fun putSerializableExtra(bundle: Bundle, vararg serializable: Serializable?) {
            with(bundle).putSerializableExtra(*serializable)
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun putSerializableExtra(activity: Activity, vararg serializable: Serializable?) {
            with(activity).putSerializableExtra(*serializable)
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun <T : Serializable> readSerializableExtra(activity: Activity, cls: Class<T>, defaultValue: T?): T? {
            return with(activity).readExtra(cls, defaultValue)
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun <T : Serializable> readSerializableExtra(activity: Activity, cls: Class<T>): T? {
            return with(activity).readExtra(cls)
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun readSerializableExtra(activity: Activity): Serializable? {
            return with(activity).getSerializableExtra()
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun <T : Serializable> readSerializableExtra(intent: Intent, cls: Class<T>): T? {
            return with(intent).readExtra(cls)
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun readSerializableExtra(intent: Intent): Array<Serializable?>? {
            return with(intent).getSerializableExtra()
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun readSerializableExtra(activity: Activity, index: Int): Serializable? {
            return with(activity).readExtra(Serializable::class.java, index, null)
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun readSerializableExtra(intent: Intent, index: Int): Serializable? {
            return with(intent).readExtra(Serializable::class.java, index, null)
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        inline fun <reified T : Serializable> readSerializableExtra(activity: Activity, defaultValue: T?): T? {
            return with(activity).readExtra(defaultValue)
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun <T : Serializable> readSerializableExtra(activity: Activity, cls: Class<T>, index: Int, defaultValue: T?): T? {
            return with(activity).readExtra(cls, index, defaultValue)
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun <T : Serializable> readSerializableExtra(intent: Intent, cls: Class<T>, defaultValue: T?): T? {
            return with(intent).readExtra(cls, defaultValue)
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun <T : Serializable> readSerializableExtraList(activity: Activity, cls: Class<T>): List<T> {
            return with(activity).readExtraList(cls)
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T : Serializable> readSerializableExtraList(intent: Intent, cls: Class<T>): List<T> {
            return with(intent).readExtraList(cls)
        }

        @Deprecated("use XIntent.with()")
        @JvmStatic
        fun <T : Serializable> readSerializableExtra(intent: Intent, cls: Class<T>, index: Int, defaultValue: T?): T? {
            return with(intent).readExtra(cls, index, defaultValue)
        }
        //endregion
    }
}

inline fun <reified T : Serializable> Activity.readExtra(defaultValue: T? = null): T? {
    return XIntent.with(this).readExtra(defaultValue)
}

inline fun <reified T : Serializable> Activity.readExtraList(): List<T> {
    return XIntent.with(this).readExtraList()
}

@Deprecated("use readExtraList", replaceWith = ReplaceWith("readExtraList()"))
inline fun <reified T : Serializable> Activity.readExtraOfList(): List<T> {
    return readExtraList()
}

@Deprecated("use readExtraList", replaceWith = ReplaceWith("readExtraList()"))
inline fun <reified T : Serializable> Activity.readListExtra(): List<T> {
    return readExtraList()
}