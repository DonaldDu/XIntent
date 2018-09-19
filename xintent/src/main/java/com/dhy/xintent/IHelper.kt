package com.dhy.xintent

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import com.dhy.xintent.adapter.IPopMenuAdapter
import com.dhy.xintent.data.ArrowType
import com.dhy.xintent.data.DefaultInputSetting
import com.dhy.xintent.data.PopMenuSetting

interface IHelper {

    val isNotUiThread: Boolean
    fun null2empty(input: String?): String

    fun loadData(from: Any, to: Any, commonClass: Class<*>)

    fun enableDialogInputMethod(dialog: Dialog)

    /**
     * @param layoutId xml根节点可以设置margin
     */
    fun showDialog(context: Context, @LayoutRes layoutId: Int, canceledOnTouchOutside: Boolean = true): Dialog

    fun showDialog(context: Context, message: String, canceledOnTouchOutside: Boolean = true): Dialog

    fun showActionDone(context: Context, canceledOnTouchOutside: Boolean = true, onDismissAction: Runnable? = null): Dialog

    fun showProgressDialog(context: Context): Dialog

    fun dismissProgressDialog(context: Context)

    fun showDefaultInputDialog(context: Context, setting: DefaultInputSetting, canceledOnTouchOutside: Boolean = true, callback: (String) -> Unit)

    /**
     * @param callback callback commit or not
     */
    fun showDefaultConfirmDialog(context: Context, msg: String, commit: String = "确定", cancel: String = "取消", canceledOnTouchOutside: Boolean = true, callback: (Boolean) -> Unit)

    fun showAsSplitedText(editText: EditText, parts: IntArray, split: String): TextWatcher

    fun showDefaultText(tv: TextView)

    fun showSecondaryText(tv: TextView)

    /**
     * between default and secondray
     */
    fun troggleText(tv: TextView)

    fun isMobileNumber(editText: EditText): Boolean

    fun isWeixinInstalled(context: Context): Boolean

    fun showInputMethod(context: Activity, edit: EditText?, show: Boolean)

    fun <T> createPopMenu(view: View, adapter: IPopMenuAdapter<T>, show: Boolean, setting: PopMenuSetting?): PopupWindow

    fun createPopMenu(view: View, adapter: IPopMenuAdapter<*>): PopupWindow

    fun getDisplayMetrics(context: Context): DisplayMetrics

    fun getCompoundDrawable(tv: TextView, type: ArrowType): Drawable?

    /**
     * 在xml中设置好各种状态的图标，然后在代码中指定显示的状态。注意：只能显示一个状态
     */
    fun showCompoundDrawable(tv: TextView, type: ArrowType)

    fun initGroupAsListView(group: ViewGroup, adapter: BaseAdapter)

    /**
     * 打开浏览器 下载新版apk
     *
     * @param apkUrl apk托管地址
     */
    fun openBrowserForUpdate(context: Context, apkUrl: String)

    fun append(vararg values: Any?): String

    fun emptyForNull(value: String?): String

    fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double

    fun showAddressInMap(context: Context, address: String)

    fun objectToJson(obj: Any): String

    fun show(container: View, @IdRes id: Int, trueShowFalseGone: Boolean): View

    fun setItemInfo(viewGroup: ViewGroup, index: Int, key: String, value: String?)
}
