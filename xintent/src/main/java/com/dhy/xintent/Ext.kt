package com.dhy.xintent

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.support.annotation.IdRes
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

fun Boolean.toInt(): Int {
    return if (this) 1 else 0
}

fun Boolean?.isNullOrFalse(): Boolean {
    return this != true
}

fun Boolean?.isNullOrTrue(): Boolean {
    return this != false
}

fun <V : View> V.show(show: Boolean = true): V {
    visibility = if (show) View.VISIBLE else View.GONE
    return this
}

fun <V : View> V.show(@IdRes viewId: Int, show: Boolean = true): V {
    val view = findViewById<View>(viewId)
    view.visibility = if (show) View.VISIBLE else View.GONE
    return this
}

fun <V : View> V.visible(show: Boolean = true): V {
    visibility = if (show) View.VISIBLE else View.INVISIBLE
    return this
}

fun <V : View> V.gone(): V {
    visibility = View.GONE
    return this
}

fun View.findParent(test: (v: ViewGroup) -> Boolean): ViewGroup? {
    val v = this.parent
    return if (v is ViewGroup) {
        if (test(v)) v
        else v.findParent(test)
    } else null
}

fun EditText.actionSearch(action: (String) -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            action(text.toString())
            true
        } else false
    }
}

fun EditText.afterTextChanged(action: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            action(text.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }
    })
}

fun TextView.isEmpty(): Boolean {
    return length() == 0
}

fun TextView.isNotEmpty(): Boolean {
    return length() > 0
}

fun <T : TextView> T.formatText(vararg param: Any?): T {
    val f = contentDescription?.toString()
    text = f?.format(*param) ?: param.toString()
    return this
}

fun Dialog.bottomGravity(): Dialog {
    window?.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
    return this
}

fun Context.toast(tip: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, tip, duration).show()
}

/**
 * @param edit not null for auto show InputMethod
 * */
fun Dialog.enableInputMethod(edit: EditText? = null): Dialog {
    val window = window
    if (window != null) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        val mode = if (edit != null) WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        else WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        window.setSoftInputMode(mode)
    }
    return this
}

/**
 * must init after super.setContentView
 * */
fun Activity.smartEditCursor() {
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    val rect = Rect()
    val mDecorView = window.decorView
    val keyboadHeight = resources.displayMetrics.density * 150//dp
    val onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        if (v is EditText) v.isCursorVisible = hasFocus
    }
    mDecorView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        fun setCursorVisible(show: Boolean) {
            val v = currentFocus
            if (v is EditText) {
                v.isCursorVisible = show
                if (!show) v.setOnFocusChangeListener(onFocusChangeListener)
            }
        }

        var isKeyboadShow = false
        override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
            mDecorView.getWindowVisibleDisplayFrame(rect)

            if (bottom - rect.bottom > keyboadHeight) {//不能用(bottom!=rect.bottom) 来判断，因为有些手机的软键盘，一出来高度就不等了。
                if (!isKeyboadShow) {
                    isKeyboadShow = true
                    setCursorVisible(isKeyboadShow)
                }
            } else {
                if (isKeyboadShow) {
                    isKeyboadShow = false
                    setCursorVisible(isKeyboadShow)
                }
            }
        }
    })
    fun findFirstEdit(vg: ViewGroup): EditText? {
        for (i in 0 until vg.childCount) {
            val v = vg.getChildAt(i)
            if (v is EditText) return v
            else if (v is ViewGroup) {
                val find = findFirstEdit(v)
                if (find != null) return find
            }
        }
        return null
    }

    val et = findFirstEdit(mDecorView as ViewGroup)
    if (et != null) et.isCursorVisible = false
}

fun String?.isNotEmpty(): Boolean {
    return !TextUtils.isEmpty(this)
}
