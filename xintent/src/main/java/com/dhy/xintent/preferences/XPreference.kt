package com.dhy.xintent.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.dhy.xintent.XCommon
import org.apache.commons.io.FileUtils
import org.json.JSONObject
import java.io.File
import java.nio.charset.Charset

class XPreference(context: Context, isStatic: Boolean = false, preference: IPreferenceKT = if (isStatic) StaticPreference(context) else InnerPreference(context)) : IPreferenceKT by preference {
    companion object {
        var converter: ObjectConverter = GsonConverter()
        var generator: IPreferenceFileNameGenerator = object : IPreferenceFileNameGenerator {}
    }

    inline fun <reified T> get(): T? {
        return get(T::class.java.name, T::class.java)
    }

    inline fun <reified T> get(key: String): T? {
        return get(key, T::class.java)
    }

    inline fun <reified T> get(key: Enum<*>): T? {
        return get(getEnumKey(key), T::class.java)
    }
}

class InnerPreference(val context: Context) : IPreferenceKT {
    override fun putString(key: String, obj: String?) {
        if (obj != null) {
            getSharedPreferences(key).edit().apply {
                putString(key, obj)
                apply()
            }
        } else {
            getSharedPreferences(key).edit().clear().apply()
        }
    }

    override fun getString(key: String): String? {
        return getSharedPreferences(key).getString(key, null)
    }

    private fun getSharedPreferences(key: String): SharedPreferences {
        val preferencesName = XPreference.generator.generate(key)
        return context.getSharedPreferences(preferencesName, Activity.MODE_PRIVATE)
    }
}

class StaticPreference(val context: Context) : IPreferenceKT {
    private val folder = XCommon.getStaticDirectory(context)
    override fun putString(key: String, obj: String?) {
        val file = getPrefsFile(folder, key)
        if (obj != null) {
            val jsonObject = JSONObject()
            jsonObject.put(key, obj)
            FileUtils.writeStringToFile(file, jsonObject.toString(), Charset.defaultCharset())
        } else {
            if (file.exists()) file.delete()
        }
    }

    override fun getString(key: String): String? {
        val file = getPrefsFile(folder, key)
        if (file.exists()) {
            val json = FileUtils.readFileToString(file, Charset.defaultCharset())
            if (!TextUtils.isEmpty(json)) {
                val jsonObject = JSONObject(json)
                return jsonObject.opt(key) as String?
            }
        }
        return null
    }

    private fun getPrefsFile(root: File, key: String): File {
        val preferencesName = XPreference.generator.generate(key)
        val f = File(root, "prefs" + File.separator + preferencesName)
        f.parentFile.mkdirs()
        return f
    }
}