package com.dhy.xintent.json

import com.google.gson.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class DateDeserializer : JsonDeserializer<Date> {
    private val FULL_STRING = "yyyy-MM-dd HH:mm:ss"
    private val FULL_SIZE = FULL_STRING.length
    private val FULL = SimpleDateFormat(FULL_STRING)
    private val FULLT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") //2019-09-04T20:21:32
    private val MINUTE = SimpleDateFormat("yyyy-MM-dd HH:mm")
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): Date {
        val s = json.asString
        if (s != null && s.isNotEmpty()) {
            if (s.contains("-")) {
                val f = if (s.length == FULL_SIZE) {
                    if (s.contains("T")) FULLT else FULL
                } else MINUTE

                try {
                    return f.parse(s)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {//number
                try {
                    return Date(java.lang.Long.parseLong(s))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return Date(0)
    }

    companion object {
        @JvmStatic
        fun registerTypeAdapter(builder: GsonBuilder) {
            builder.registerTypeAdapter(Date::class.java, DateDeserializer())
        }
    }
}
