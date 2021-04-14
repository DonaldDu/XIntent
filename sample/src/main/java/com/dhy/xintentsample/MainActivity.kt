package com.dhy.xintentsample

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.dhy.xintent.XIntent
import com.dhy.xintent.putSerializableExtra
import com.dhy.xintent.readExtra
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private val KEY_TRUE = "key_true"
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        XIntent.with(outState).putSerializableExtra(true)
        Intent().replaceExtras(outState).putExtra(KEY_TRUE, true)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val ok: Boolean? = XIntent.with(savedInstanceState).readExtra()
        val vTrue = savedInstanceState.getBoolean(KEY_TRUE)
        tvOnRestoreInstanceState.text = "$ok/$vTrue"
    }
}