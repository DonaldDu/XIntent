package com.dhy.xintentsample

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.dhy.xintent.XCommon
import com.dhy.xintent.XIntent
import com.dhy.xintent.XIntent.Companion.readSerializableExtra
import com.dhy.xintent.XIntent.Companion.startActivity
import com.dhy.xintent.readExtra
import com.dhy.xintent.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}