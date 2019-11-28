package com.dhy.xintent.preferences

interface IPreferenceFileNameGenerator {
    fun generate(keyName: String): String {
        return keyName
    }
}