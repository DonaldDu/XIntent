package com.dhy.xintentsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dhy.xintent.XIntent
import com.dhy.xintent.readExtra
import kotlinx.android.synthetic.main.activity_send_extra.*

class SendExtraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_extra)

        btSend.setOnClickListener {
            val sendTime = System.currentTimeMillis()
            val intent = XIntent(this, ReceiveExtraActivity::class, sendTime)
            startActivityForResult(intent, 1)

            XIntent.startActivity(this, ReceiveExtraActivity::class, sendTime)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val backTime: Long? = XIntent.with(data).readExtra()
    }
}