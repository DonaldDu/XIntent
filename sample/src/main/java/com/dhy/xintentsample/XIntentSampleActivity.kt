package com.dhy.xintentsample

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.dhy.xintent.XCommon
import com.dhy.xintent.XIntent
import com.dhy.xintent.XIntent.Companion.putSerializableExtra
import com.dhy.xintent.XIntent.Companion.readSerializableExtra
import com.dhy.xintent.XIntent.Companion.startActivity
import com.dhy.xintent.readExtra
import com.dhy.xintent.toast
import kotlinx.android.synthetic.main.activity_main.*

class XIntentSampleActivity : Activity() {
    var tag = XIntentSampleActivity::class.java.simpleName
    var init = false
    var context: Context? = null
    var hash = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        val msg: String? = readExtra()
        XCommon.setTextWithFormat(this, R.id.textView, msg)
        if (savedInstanceState != null) {
            val intent: Intent = XIntent(savedInstanceState)
            hash = readSerializableExtra(intent, Int::class.java, -1)!!
        }
        Log.i(tag, "init $init")
        Log.i(tag, "hash1 $hash")
        init = true
        hash = findViewById<View>(R.id.textView).hashCode()
        Log.i(tag, "hash2 $hash")
        btToast.setOnClickListener {
            toast("test hash2hash2hash2hash2")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        putSerializableExtra(outState, "s", 1)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val intent: Intent = XIntent(savedInstanceState)
        val a = readSerializableExtra(intent, String::class.java, "")!!
        val b = readSerializableExtra(intent, Int::class.java, 0)!!
    }

    fun startIntent(view: View?) {
        val editText = findViewById<EditText>(R.id.editText)
        val text = editText.text.toString()
        if (text.length > 2) {
            startActivity(context!!, XIntentSampleActivity::class.java, text)
        } else {
            val intent: Intent = XIntent(context, XIntentSampleActivity::class.java, text)
            startActivity(intent)
        }
    }

    fun testActivity(view: View?) {
        startActivity(Intent(context, TestActivity::class.java))
    }
}