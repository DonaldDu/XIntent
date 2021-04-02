package com.dhy.xintentsample

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.dhy.xintent.XIntent
import com.dhy.xintent.formatText
import com.dhy.xintent.putSerializableExtra
import com.dhy.xintent.readExtra
import kotlinx.android.synthetic.main.activity_receive_extra.*
import java.text.SimpleDateFormat

class ReceiveExtraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_extra)

        val sendTimeMS: Long? = readExtra()

        if (sendTimeMS != null) {
            val f = SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SSS")
            tvTime.formatText(f.format(sendTimeMS))
        } else tvTime.text = "receive nothing"
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        val saveTime = System.currentTimeMillis()
        val user = "userName"
        val pwd = 123
        val newUser = true
        XIntent.with(outState).putSerializableExtra(saveTime, user, pwd, newUser)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        XIntent.with(savedInstanceState).apply {
            val saveTime: Long? = readExtra()
            val user: String? = readExtra()
            val pwd: Int? = readExtra()
            val newUser: Boolean? = readExtra()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        XIntent.with().apply {
            val backTime = System.currentTimeMillis()
            putSerializableExtra(backTime)
            setResult(RESULT_OK, intent)
        }
    }
}