package com.dhy.xintent.des

import java.io.Serializable

interface IntentWrapperDes {
    fun putSerializableExtra(vararg serializable: Any?)
    fun getSerializableExtra(): Array<Serializable?>?
//    inline fun <reified T : Serializable> readExtra(defaultValue: T? = null): T?
//    inline fun <reified T : Serializable> readExtra(index: Int, defaultValue: T? = null): T?
//    inline fun <reified T : Serializable> readExtraList(): List<T>
}