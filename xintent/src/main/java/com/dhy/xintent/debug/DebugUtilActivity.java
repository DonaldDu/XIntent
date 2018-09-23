package com.dhy.xintent.debug;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhy.xintent.XIntent;
import com.dhy.xintent.preferences.XPreferences;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@DebugUtilActivity.Ignored
public class DebugUtilActivity extends AppCompatActivity {
    public static void initOnViewLongClick(final Activity activity, @IdRes int viewId) {
        activity.findViewById(viewId).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DebugUtilActivity.showMenu(activity);
                return true;
            }
        });
    }

    private Context context;
    private static final String ACTION_MENU = "com.dhy.menu";
    private static final String ACTION_START_ACTIVITY = "com.dhy.startActivity";
    private static final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("TAG", "DebugUtilActivity onReceive Action: " + action);
            if (action != null) switch (action) {
                case ACTION_MENU:
                    showMenu(context);
                    break;
                case ACTION_START_ACTIVITY:
                    startActivity(context, intent);
                    break;
            }
        }
    };

    public static void register(Application application, @NonNull String activitySourceHeader) {
        ACTIVITY_SOURCE_HEADER = activitySourceHeader;
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_MENU);
        filter.addAction(ACTION_START_ACTIVITY);
        try {
            application.unregisterReceiver(receiver);
        } catch (Exception ignored) {
        }
        application.registerReceiver(receiver, filter);
    }

    private static void showMenu(Context context) {
        Intent i = new Intent(context, DebugUtilActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @NonNull
    private static String getStarterName() {
        return DebugUtilActivity.class.getName();
    }

    private static void startActivity(Context context, Intent intent) {
        String className = intent.getStringExtra("className");
        try {
            intent = new Intent(context, Class.forName(className));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean autoStart(Context context) {
        String name = XPreferences.get(context, Key.autoStart, String.class);
        if (!TextUtils.isEmpty(name)) {
            ActivityItem.startActivity(context, name);
            if (context instanceof Activity) ((Activity) context).finish();
            return true;
        }
        return false;
    }

    public static boolean isDebugStarted(Activity activity) {
        String starter = XIntent.readSerializableExtra(activity, String.class);
        return getStarterName().equals(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        final ExpandableListView expandableListView = new ExpandableListView(context);
        expandableListView.setGroupIndicator(null);
        expandableListView.setAdapter(new MyAdapter(getAllActivities()));
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(expandableListView);
        dialog.show();
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                dialog.dismiss();
                if (groupPosition + childPosition != 0) {
                    ActivityItem item = (ActivityItem) expandableListView.getExpandableListAdapter().getChild(groupPosition, childPosition);
                    item.startActivity(context);
                } else {
                    XPreferences.set(context, Key.autoStart, null);
                }
                return false;
            }
        });
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityItem item = (ActivityItem) view.getTag();
                if (item != null && item.cls != null) {
                    dialog.dismiss();
                    boolean ok = item.startActivity(context);
                    if (ok) XPreferences.set(context, Key.autoStart, item.cls);
                    return true;
                }
                return false;
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
    }

    public List<ActivityItem> getAllActivities() {
        List<ActivityItem> list = new ArrayList<>();
        try {
            ActivityInfo[] activities = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES).activities;
            if (activities != null) {
                for (ActivityInfo activity : activities) {
                    if (activity.name.startsWith(ACTIVITY_SOURCE_HEADER) && !isIgnore(activity.name)) {
                        list.add(new ActivityItem(activity.name));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void addAutoStartItem(List<ActivityItem> list) {
        ActivityItem autoStart = new ActivityItem(null);
        autoStart.name = AUTO_START;
        list.add(0, autoStart);
    }

    private boolean isIgnore(String className) {
        try {
            Class<?> cls = Class.forName(className);
            Ignored ignored = cls.getAnnotation(Ignored.class);
            return ignored != null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Ignored {
    }


    private static final String AUTO_START = "Click this to cancel AUTO_START\nLongClick others to set as AUTO_START";

    private enum Key {
        autoStart
    }

    private static class ActivityItem implements StringGetter {
        String cls;
        String name;

        ActivityItem(@Nullable String cls) {
            this.cls = cls;
            if (cls != null) {
                this.name = cls.substring(cls.lastIndexOf(".") + 1);
            }
        }

        @Override
        public String toString() {
            return name;
        }

        boolean startActivity(Context context) {
            return startActivity(context, cls);
        }

        static boolean startActivity(Context context, String cls) {
            try {
                Class activity = Class.forName(cls);
                XIntent.startActivity(context, activity, getStarterName());
                return true;
            } catch (ClassNotFoundException e) {
                Toast.makeText(context, "Error:" + cls, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public String getString() {
            return cls;
        }
    }

    private interface StringGetter {
        String getString();
    }

    public static String ACTIVITY_SOURCE_HEADER = "";

    private static class GroupItem {
        String name;
        List<ActivityItem> kids = new ArrayList<>();

        static List<GroupItem> groupItems(List<ActivityItem> list) {
            sortActivitys(list);
            addAutoStartItem(list);
            List<GroupItem> groupItems = new ArrayList<>();
            GroupItem groupItem = null;
            int start = ACTIVITY_SOURCE_HEADER.length() + 1;
            for (ActivityItem item : list) {
                String header;
                if (item.cls != null) {
                    header = item.cls.substring(0, item.cls.lastIndexOf("."));
                    header = header.substring(start);
                } else {
                    header = "main";
                }
                if (groupItem == null || !groupItem.name.equals(header)) {
                    groupItem = new GroupItem();
                    groupItem.name = header;
                    groupItems.add(groupItem);
                }
                groupItem.kids.add(item);
            }
            return groupItems;
        }

        private static void sortActivitys(List<ActivityItem> list) {
            Collections.sort(list, new Comparator<ActivityItem>() {
                @Override
                public int compare(ActivityItem a, ActivityItem b) {
                    return a.cls.compareTo(b.cls);
                }
            });
        }
    }

    private class MyAdapter extends BaseExpandableListAdapter {
        List<GroupItem> list;
        LayoutInflater inflater;

        MyAdapter(List<ActivityItem> list) {
            this.list = GroupItem.groupItems(list);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getGroupCount() {
            return list.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return list.get(groupPosition).kids.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return list.get(groupPosition);
        }

        @Override
        public ActivityItem getChild(int groupPosition, int childPosition) {
            return getGroup(groupPosition).kids.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView = getItemView(convertView, parent);
            TextView textView = (TextView) convertView;
            textView.setText(getGroup(groupPosition).name);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = getItemView(convertView, parent);
            ActivityItem item = getChild(groupPosition, childPosition);
            convertView.setTag(item);
            TextView textView = (TextView) convertView;
            textView.setText(item.name);
            convertView.setBackgroundColor(Color.parseColor("#FF72946B"));

            return convertView;
        }

        private View getItemView(View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                convertView.setMinimumWidth(3000);
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
