package com.dhy.xintent.debug

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.dhy.xintent.XIntent
import com.dhy.xintent.preferences.XPreferences
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*

@DebugUtilActivity.Ignored
class DebugUtilActivity : AppCompatActivity() {

    private var context: Context? = null

    val allActivities: MutableList<ActivityItem>
        get() {
            val list = ArrayList<ActivityItem>()
            try {
                val activities = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).activities
                if (activities != null) {
                    for (activity in activities) {
                        if (activity.name.startsWith(ACTIVITY_SOURCE_HEADER) && !isIgnore(activity.name)) {
                            list.add(ActivityItem(activity.name))
                        }
                    }
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return list
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        val expandableListView = ExpandableListView(context)
        expandableListView.setGroupIndicator(null)
        expandableListView.setAdapter(MyAdapter(allActivities))
        val dialog = Dialog(this)
        dialog.setContentView(expandableListView)
        dialog.show()
        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            dialog.dismiss()
            if (groupPosition + childPosition != 0) {
                val item = expandableListView.expandableListAdapter.getChild(groupPosition, childPosition) as ActivityItem
                item.startActivity(context)
            } else {
                XPreferences.set(context, Key.autoStart, null)
            }
            false
        }
        expandableListView.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
            val item = view.tag as ActivityItem
            if (item != null && item.string != null) {
                dialog.dismiss()
                val ok = item.startActivity(context)
                if (ok) XPreferences.set(context, Key.autoStart, item.string)
                return@OnItemLongClickListener true
            }
            false
        }
        dialog.setOnDismissListener { finish() }
    }

    private fun isIgnore(className: String): Boolean {
        try {
            val cls = Class.forName(className)
            val ignored = cls.getAnnotation(Ignored::class.java)
            return ignored != null
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return false
    }

    @Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
    @Retention(RetentionPolicy.RUNTIME)
    annotation class Ignored

    private enum class Key {
        autoStart
    }

    class ActivityItem internal constructor(private val cls: String?) : StringGetter {

        override val string: String
            get() {
                return cls ?: ""
            }
        internal lateinit var name: String

        init {
            if (cls != null) {
                this.name = cls.substring(cls.lastIndexOf(".") + 1)
            }
        }

        override fun toString(): String {
            return name
        }

        internal fun startActivity(context: Context?): Boolean {
            return startActivity(context, string)
        }

        companion object {
            internal fun startActivity(context: Context?, cls: String?): Boolean {
                try {
                    val activity = Class.forName(cls!!) as Class<Activity>
                    XIntent.startActivity(context!!, activity, starterName)
                    return true
                } catch (e: ClassNotFoundException) {
                    Toast.makeText(context, "Error:" + cls!!, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                    return false
                }

            }
        }
    }

    private interface StringGetter {
        val string: String
    }

    private class GroupItem {
        internal lateinit var name: String
        internal var kids: MutableList<ActivityItem> = ArrayList()

        companion object {

            internal fun groupItems(list: MutableList<ActivityItem>): List<GroupItem> {
                sortActivitys(list)
                addAutoStartItem(list)
                val groupItems = ArrayList<GroupItem>()
                var groupItem: GroupItem? = null
                val start = ACTIVITY_SOURCE_HEADER.length + 1
                for (item in list) {
                    var header: String
                    if (item.string != null) {
                        header = item.string!!.substring(0, item.string!!.lastIndexOf("."))
                        header = header.substring(start)
                    } else {
                        header = "main"
                    }
                    if (groupItem == null || groupItem.name != header) {
                        groupItem = GroupItem()
                        groupItem.name = header
                        groupItems.add(groupItem)
                    }
                    groupItem.kids.add(item)
                }
                return groupItems
            }

            private fun sortActivitys(list: List<ActivityItem>) {
                Collections.sort(list) { a, b -> a.string!!.compareTo(b.string!!) }
            }
        }
    }

    private inner class MyAdapter internal constructor(list: MutableList<ActivityItem>) : BaseExpandableListAdapter() {
        internal var list: List<GroupItem> = GroupItem.groupItems(list)
        internal var inflater: LayoutInflater = LayoutInflater.from(context)

        override fun getGroupCount(): Int {
            return list.size
        }

        override fun getChildrenCount(groupPosition: Int): Int {
            return list[groupPosition].kids.size
        }

        override fun getGroup(groupPosition: Int): GroupItem {
            return list[groupPosition]
        }

        override fun getChild(groupPosition: Int, childPosition: Int): ActivityItem {
            return getGroup(groupPosition).kids[childPosition]
        }

        override fun getGroupId(groupPosition: Int): Long {
            return 0
        }

        override fun getChildId(groupPosition: Int, childPosition: Int): Long {
            return 0
        }

        override fun hasStableIds(): Boolean {
            return false
        }

        override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View, parent: ViewGroup): View {
            var convertView = convertView
            convertView = getItemView(convertView, parent)
            val textView = convertView as TextView
            textView.text = getGroup(groupPosition).name
            return convertView
        }

        override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View, parent: ViewGroup): View {
            var convertView = convertView
            convertView = getItemView(convertView, parent)
            val item = getChild(groupPosition, childPosition)
            convertView.tag = item
            val textView = convertView as TextView
            textView.text = item.name
            convertView.setBackgroundColor(Color.parseColor("#FF72946B"))

            return convertView
        }

        private fun getItemView(convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            if (convertView == null) {
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
                convertView!!.minimumWidth = 3000
            }
            return convertView
        }

        override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
            return true
        }
    }

    companion object {
        fun initOnViewLongClick(activity: Activity, @IdRes viewId: Int) {
            activity.findViewById<View>(viewId).setOnLongClickListener {
                DebugUtilActivity.showMenu(activity)
                true
            }
        }

        private val ACTION_MENU = "com.dhy.menu"
        private val ACTION_START_ACTIVITY = "com.dhy.startActivity"
        private val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                Log.i("TAG", "DebugUtilActivity onReceive Action: " + action!!)
                if (action != null)
                    when (action) {
                        ACTION_MENU -> showMenu(context)
                        ACTION_START_ACTIVITY -> startActivity(context, intent)
                    }
            }
        }

        fun register(application: Application, activitySourceHeader: String) {
            ACTIVITY_SOURCE_HEADER = activitySourceHeader
            val filter = IntentFilter()
            filter.addAction(ACTION_MENU)
            filter.addAction(ACTION_START_ACTIVITY)
            try {
                application.unregisterReceiver(receiver)
            } catch (ignored: Exception) {
            }

            application.registerReceiver(receiver, filter)
        }

        private fun showMenu(context: Context) {
            val i = Intent(context, DebugUtilActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }

        private val starterName: String
            get() = DebugUtilActivity::class.java.name

        private fun startActivity(context: Context, intent: Intent) {
            var intent = intent
            val className = intent.getStringExtra("className")
            try {
                intent = Intent(context, Class.forName(className))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

        }

        fun autoStart(context: Context): Boolean {
            val name = XPreferences.get(context, Key.autoStart, String::class.java)
            if (!TextUtils.isEmpty(name)) {
                ActivityItem.startActivity(context, name)
                if (context is Activity) context.finish()
                return true
            }
            return false
        }

        fun isDebugStarted(activity: Activity): Boolean {
            val starter = XIntent.readSerializableExtra(activity, String::class.java)
            return starterName == starter
        }

        private fun addAutoStartItem(list: MutableList<ActivityItem>) {
            val autoStart = ActivityItem(null)
            autoStart.name = AUTO_START
            list.add(0, autoStart)
        }


        private val AUTO_START = "Click this to cancel AUTO_START\nLongClick others to set as AUTO_START"

        var ACTIVITY_SOURCE_HEADER = ""
    }
}
