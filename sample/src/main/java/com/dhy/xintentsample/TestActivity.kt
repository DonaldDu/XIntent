package com.dhy.xintentsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.dhy.xintent.XIntent
import com.dhy.xintent.readExtra

/**
 * Created by donald on 2016/2/5.
 */
class TestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity_layout)
    }

    fun usage() {
        val KEY_USER_NAME = "key_user_name"
        val KEY_USER_PWD = "key_user_pwd"
        val KEY_USER_NEW = "key_user_new"

        val intent = Intent()
        intent.putExtra(KEY_USER_NAME, "name")
        intent.putExtra(KEY_USER_PWD, 123)
        intent.putExtra(KEY_USER_NEW, true)

        val name = intent.getStringExtra(KEY_USER_NAME)
        val pwd = intent.getIntExtra(KEY_USER_PWD, -1)
        val newUser = intent.getBooleanExtra(KEY_USER_NEW, false)

        XIntent.startActivity(this, MainActivity::class, name, pwd, newUser)

//        val intent = XIntent(this, MainActivity::class, name, pwd, newUser)
//        startActivityForResult(intent, 1)
    }

    fun readData() {
        XIntent.with(this).apply {
            val name: String = readExtra()!!
            val pwd: Int = readExtra()!!
            val newUser: Boolean = readExtra()!!
        }
    }
}