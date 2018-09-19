package com.dhy.xintent.implement

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.dhy.xintent.IHelper
import com.dhy.xintent.R
import com.dhy.xintent.XCommon
import com.dhy.xintent.adapter.IPopMenuAdapter
import com.dhy.xintent.data.ArrowType
import com.dhy.xintent.data.DefaultInputSetting
import com.dhy.xintent.data.PopMenuSetting
import com.dhy.xintent.simple.SimpleActivityLifecycleCallbacks
import com.dhy.xintent.simple.SimpleTextWatcher
import com.dhy.xintent.style.SplitSpan
import com.google.gson.Gson
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.*

open class Helper(application: Application, @StyleRes private val themeDialog: Int, private val debug: Boolean) : IHelper {
    override fun isNotEmpty(vararg textViews: TextView): Boolean {
        for (textView in textViews) {
            if (textView.length() == 0) {
                Toast.makeText(textView.context, textView.hint, Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    override fun scrollToBottom(scroll: ScrollView?) {
        if (scroll == null) return
        Handler().post {
            var offset = scroll.getChildAt(0).measuredHeight - scroll.height
            if (offset < 0) offset = 0
            scroll.scrollTo(0, offset)
        }
    }

    override fun setVisibility(visibility: Int, vararg views: View) {
        for (view in views) {
            view.visibility = visibility
        }
    }

    override fun append(vararg values: Any?): String {
        val sb = StringBuilder()
        for (value in values) {
            if (value != null) sb.append(value)
        }
        return sb.toString()
    }

    override fun emptyForNull(value: String?): String {
        return value ?: ""
    }

    override fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val jl_jd = 102834.74258026089786013677476285
        val jl_wd = 111712.69150641055729984301412873
        val a = Math.abs((lat1 - lat2) * jl_wd)
        val b = Math.abs((lon1 - lon2) * jl_jd)
        return Math.sqrt(a * a + b * b)
    }

    override fun showAddressInMap(context: Context, address: String) {
        try {
            val uri = Uri.parse("geo:,?q=$address")
            val mIntent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(mIntent)
        } catch (e: Exception) {
            showDialog(context, "请安装一个地图应用")
        }
    }

    private val gson = Gson()
    override fun objectToJson(obj: Any): String {
        return gson.toJson(obj)
    }

    override fun show(container: View, id: Int, trueShowFalseGone: Boolean): View {
        val view = container.findViewById<View>(id)
        if (view != null) view.visibility = if (trueShowFalseGone) View.VISIBLE else View.GONE
        return view
    }

    override fun setItemInfo(viewGroup: ViewGroup, index: Int, key: String, value: String?) {
        setItemInfo(viewGroup.getChildAt(index), key, value)
    }

    private fun setItemInfo(textView: View, key: String, value: String?) {
        (textView as TextView).text = key + "：" + (value ?: "")
    }

    override val isNotUiThread: Boolean
        get() = Looper.myLooper() != Looper.getMainLooper()

    private val progressDialogs = HashMap<Context, Dialog>()

    init {
        application.registerActivityLifecycleCallbacks(object : SimpleActivityLifecycleCallbacks() {
            override fun onActivityDestroyed(activity: Activity) {
                progressDialogs.remove(activity)
            }
        })
    }

    override fun null2empty(input: String?): String {
        return input ?: ""
    }

    override fun loadData(from: Any, to: Any, commonClass: Class<*>) {
        val fields = getAllFilds(commonClass)
        for (field in fields) {
            try {
                if (!field.isAccessible) field.isAccessible = true
                field.set(to, field.get(from))
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    override fun enableDialogInputMethod(dialog: Dialog) {
        val window = dialog.window
        if (window != null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        }
    }

    override fun showDialog(context: Context, layoutId: Int, canceledOnTouchOutside: Boolean): Dialog {
        if (debug && isNotUiThread) showNotRunInUiTheadTip(context)

        if (context is Activity) {
            if (context.isFinishing) {
                return Dialog(context)
            }
        }

        val dialog = AlertDialog.Builder(context, themeDialog).show()
        val window = dialog.window
        if (window != null) {
            val lp = window.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            window.attributes = lp
        }
        if (layoutId != -1) dialog.setContentView(layoutId)
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        return dialog
    }

    private fun showNotRunInUiTheadTip(context: Context) {
        val NOT_RUN_IN_UI_THREAD_ERROR = "ERROR: you are not running this code in ui thread!"
        Log.e("TAG", "*******************************************************************")
        Log.e("TAG", "***      ERROR: you are not running this code in UI thread!     ***")
        Log.e("TAG", "*******************************************************************")
        if (context is Activity) {
            context.runOnUiThread { Toast.makeText(context, NOT_RUN_IN_UI_THREAD_ERROR, Toast.LENGTH_LONG).show() }
        }
        throw IllegalStateException(NOT_RUN_IN_UI_THREAD_ERROR)
    }

    override fun showDialog(context: Context, message: String, canceledOnTouchOutside: Boolean): Dialog {
        val dialog = showDialog(context, R.layout.xintent_default_tip_dialog, canceledOnTouchOutside)
        XCommon.setText(dialog, R.id.textViewMessage, message)
        dialog.findViewById<View>(R.id.buttonCommit).setOnClickListener { dialog.dismiss() }
        return dialog
    }

    override fun showActionDone(context: Context, canceledOnTouchOutside: Boolean, onDismissAction: Runnable?): Dialog {
        val dialog = showDialog(context, "操作成功", canceledOnTouchOutside)
        if (onDismissAction != null) {
            dialog.setOnDismissListener { onDismissAction.run() }
        }
        return dialog
    }

    private fun getProgressDialog(context: Context): Dialog {
        if (debug && isNotUiThread) {
            showNotRunInUiTheadTip(context)
        }
        var progressDialog: Dialog? = progressDialogs[context]
        if (progressDialog == null) {
            progressDialog = createAndShowProgressDialog(context)
            progressDialogs[context] = progressDialog
        }
        return progressDialog
    }

    private fun createAndShowProgressDialog(context: Context): Dialog {
        return showDialog(context, R.layout.xintent_default_progress_dialog, false)
    }

    override fun showProgressDialog(context: Context): Dialog {
        val progressDialog = getProgressDialog(context)
        if (!progressDialog.isShowing) progressDialog.show()
        return progressDialog
    }

    override fun dismissProgressDialog(context: Context) {
        if (debug && isNotUiThread) {
            showNotRunInUiTheadTip(context)
        }
        val progressDialog = progressDialogs[context]
        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun showDefaultInputDialog(context: Context, setting: DefaultInputSetting, canceledOnTouchOutside: Boolean, callback: (String) -> Unit) {
        val dialog = showDialog(context, R.layout.xintent_default_input_dialog, canceledOnTouchOutside)
        enableDialogInputMethod(dialog)
        val input = dialog.findViewById<EditText>(R.id.editText)
        val listener = View.OnClickListener { v ->
            if (v.id == R.id.buttonCommit) {
                if (input.length() == 0 && !setting.canEmpty) {
                    Toast.makeText(context, input.hint, Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }

                dialog.dismiss()
                callback(input.text.toString())
            } else dialog.dismiss()
        }
        if (!TextUtils.isEmpty(setting.text)) {
            input.setText(setting.text)
            input.setSelection(input.length())
        }
        input.hint = setting.hint
        if (setting.inputType != null) {
            input.inputType = setting.inputType
        }
        XCommon.setText(dialog, R.id.buttonCommit, setting.buttonCommit).setOnClickListener(listener)
        XCommon.setText(dialog, R.id.buttonCancel, setting.buttonCancel).setOnClickListener(listener)
    }

    override fun showDefaultConfirmDialog(context: Context, msg: String, commit: String, cancel: String, canceledOnTouchOutside: Boolean, callback: (Boolean) -> Unit) {
        val dialog = showDialog(context, R.layout.xintent_default_confirm_dialog, canceledOnTouchOutside)
        val listener = View.OnClickListener { v ->
            dialog.dismiss()
            callback(v.id == R.id.buttonCommit)
        }
        XCommon.setText(dialog, R.id.textViewMessage, msg)
        XCommon.setText(dialog, R.id.buttonCommit, commit).setOnClickListener(listener)
        XCommon.setText(dialog, R.id.buttonCancel, cancel).setOnClickListener(listener)
    }

    override fun showAsSplitedText(editText: EditText, parts: IntArray, split: String): TextWatcher {
        val textWatcher = object : SimpleTextWatcher() {
            var passeNext: Boolean = false

            override fun afterTextChanged(editable: Editable) {
                if (passeNext) {
                    passeNext = false
                    return
                }

                val selectionStart = editText.selectionStart
                passeNext = true
                editText.setText(SplitSpan.getSpitedText(editable.toString().trim { it <= ' ' }, parts, split))
                editText.setSelection(selectionStart)
            }
        }
        editText.addTextChangedListener(textWatcher)
        return textWatcher
    }

    override fun showDefaultText(tv: TextView) {
        val text = tv.getTag(R.id.xintent_default_text) as String?
        if (text != null) tv.text = text
    }

    override fun showSecondaryText(tv: TextView) {
        if (tv.text != tv.hint) {
            tv.setTag(R.id.xintent_default_text, tv.text)
            tv.text = tv.hint
        }
    }

    override fun troggleText(tv: TextView) {
        val isSecondary = tv.text === tv.hint
        if (isSecondary) {
            showDefaultText(tv)
        } else {
            showSecondaryText(tv)
        }
    }

    /**
     * 以1开头的11位数字
     */
    override fun isMobileNumber(editText: EditText): Boolean {
        if (editText.length() == 11) {
            val phone = editText.text.toString()
            if (phone.startsWith("1")) {
                return try {
                    phone == java.lang.Long.parseLong(phone).toString()
                } catch (e: Exception) {
                    false
                }
            }
        }
        return false
    }

    override fun isWeixinInstalled(context: Context): Boolean {
        val pm = context.packageManager
        return try {
            val info = pm.getPackageInfo("com.tencent.mm", PackageManager.GET_ACTIVITIES)
            info != null
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }

    }

    override fun showInputMethod(context: Activity, edit: EditText?, show: Boolean) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (edit != null && show) {
            edit.requestFocus()
            imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT)
        } else {
            val view = edit ?: context.currentFocus
            if (view != null) {
                imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                view.clearFocus()
            }
        }
    }

    private fun createPopupWindow(context: Context, @LayoutRes layoutId: Int, width: Int): PopupWindow {
        val contentView = LayoutInflater.from(context).inflate(layoutId, null)
        val popupWindow = PopupWindow(contentView, width, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindow.isTouchable = true
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return popupWindow
    }

    private fun insurePopupWindowEnoughHeight(view: View, popupWindow: PopupWindow) {
        val size = getDisplayMetrics(view.context)
        val xy = IntArray(2)
        view.getLocationOnScreen(xy)
        val min = 0.25f
        var y = xy[1]
        y = if ((size.heightPixels - y) * 1.0f / size.heightPixels < min) {
            -(size.heightPixels * min).toInt() - view.height
        } else {
            -1
        }
        popupWindow.showAsDropDown(view, 0, y)
    }

    override fun createPopMenu(view: View, adapter: IPopMenuAdapter<*>): PopupWindow {
        return createPopMenu(view, adapter, true, null)
    }

    override fun <T> createPopMenu(view: View, adapter: IPopMenuAdapter<T>, show: Boolean, setting: PopMenuSetting?): PopupWindow {
        var popMenuSetting = setting
        if (popMenuSetting == null) popMenuSetting = PopMenuSetting.Builder().build()
        val popupWindow = createPopupWindow(view.context, popMenuSetting!!.layoutId, popMenuSetting.popWidth)
        val rootView = popupWindow.contentView.findViewById<View>(R.id.xintent_pop_root)
        if (popMenuSetting.padding != null) {
            val padding = popMenuSetting.padding
            rootView.setPadding(padding.left, padding.top, padding.right, padding.bottom)
        }
        val contentView = rootView.findViewById<ViewGroup>(popMenuSetting.containerId)
        if (popMenuSetting.bg != null) contentView.setBackgroundResource(popMenuSetting.bg!!)
        contentView.removeAllViews()

        val inflater = LayoutInflater.from(view.context)
        val TAG = popMenuSetting.containerId
        val listener = View.OnClickListener { v ->
            popupWindow.dismiss()
            val position = v.getTag(TAG) as Int
            if (adapter.isValid(position)) {
                adapter.onUpdateMenu(view, adapter.getItemText(position))
                adapter.onItemSelected(adapter.getItemData(position), position)
            }
        }
        contentView.minimumWidth = view.width
        for (i in 0 until adapter.count) {
            val textView = inflater.inflate(popMenuSetting.itemLayoutId, contentView, false) as TextView
            textView.text = adapter.getItemText(i)
            textView.setOnClickListener(listener)
            textView.setTag(TAG, i)
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            adapter.onItemPrepared(i, textView)
            contentView.addView(textView, params)
        }
        if (show) insurePopupWindowEnoughHeight(view, popupWindow)
        return popupWindow
    }

    override fun getDisplayMetrics(context: Context): DisplayMetrics {
        return context.applicationContext.resources.displayMetrics
    }

    override fun getCompoundDrawable(tv: TextView, type: ArrowType): Drawable? {
        @Suppress("UNCHECKED_CAST")
        var drawables = tv.getTag(R.id.xintent_compound_drawables) as Array<Drawable>?
        if (drawables == null) drawables = tv.compoundDrawables
        return drawables!![type.ordinal]
    }

    override fun showCompoundDrawable(tv: TextView, type: ArrowType) {
        if (type == tv.getTag(R.id.xintent_compound_drawable_type)) return
        @Suppress("UNCHECKED_CAST")
        var drawables = tv.getTag(R.id.xintent_compound_drawables) as Array<Drawable>?
        if (drawables == null) {
            drawables = tv.compoundDrawables
            tv.setTag(R.id.xintent_compound_drawables, drawables)
        }
        val drawable = drawables!![type.ordinal]
        when (type) {
            ArrowType.left -> tv.setCompoundDrawables(drawable, null, null, null)
            ArrowType.top -> tv.setCompoundDrawables(null, drawable, null, null)
            ArrowType.right -> tv.setCompoundDrawables(null, null, drawable, null)
            ArrowType.bottom -> tv.setCompoundDrawables(null, null, null, drawable)
        }
        tv.setTag(R.id.xintent_compound_drawable_type, type)
    }

    override fun initGroupAsListView(group: ViewGroup, adapter: BaseAdapter) {
        val size = adapter.count
        while (group.childCount > size) {
            group.removeViewAt(size)
        }

        for (i in 0 until size) {
            var convertView: View? = group.getChildAt(i)
            if (convertView != null) {
                adapter.getView(i, convertView, group)
            } else {
                convertView = adapter.getView(i, null, group)
                group.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
    }

    override fun openBrowserForUpdate(context: Context, apkUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(apkUrl)
        context.startActivity(intent)
    }

    private fun getAllFilds(mClass: Class<*>): List<Field> {
        var cls = mClass
        val list = ArrayList<Field>()
        while (cls != Any::class.java) {
            val fields = cls.declaredFields
            for (f in fields) {
                if (Modifier.isStatic(f.modifiers)) {
                    continue
                }
                if (Modifier.isTransient(f.modifiers)) {
                    continue
                }
                if (f.name.startsWith("this")) {
                    continue
                }
                list.add(f)
            }
            cls = cls.superclass
        }
        return list
    }
}
