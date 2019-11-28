package com.dhy.xintent.preferences

interface IPreferenceKT {
    fun put(obj: Any) {
        put(obj::class.java.name, null)
    }

    fun put(key: Class<*>, obj: Any?) {
        put(key.name, obj)
    }

    fun put(key: Enum<*>, obj: Any?) {
        put(getEnumKey(key), obj)
    }

    fun put(key: String, obj: Any?) {
        val json = if (obj != null) {
            XPreference.converter.objectToString(obj)
        } else null
        putString(key, json)
    }

    fun putString(key: String, obj: String?)

    fun getEnumKey(key: Enum<*>): String {
        return "${key::class.java}.${key.name}"
    }

    fun <T> get(key: String, cls: Class<T>): T? {
        val json = getString(key)
        return if (json != null) {
            XPreference.converter.string2object(json, cls)
        } else null
    }

    fun getString(key: String): String?
}