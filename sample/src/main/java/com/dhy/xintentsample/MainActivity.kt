package com.dhy.xintentsample

import android.app.Activity
import android.content.Context
import android.os.Bundle

class MainActivity : Activity() {
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}