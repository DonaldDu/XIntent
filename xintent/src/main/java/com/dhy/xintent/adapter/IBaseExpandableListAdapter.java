package com.dhy.xintent.adapter;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.dhy.xintent.interfaces.Expandable;

import java.util.ArrayList;
import java.util.List;

public abstract class IBaseExpandableListAdapter<GROUP extends Expandable<CHILD>, CHILD> extends BaseExpandableListAdapter {
    protected final Context context;
    @NonNull
    public final List<GROUP> datas;
    protected final int groupLayout, childLayout;
    protected final LayoutInflater inflater;

    public IBaseExpandableListAdapter(Context context, @Nullable List<GROUP> list, @LayoutRes int groupLayout, @LayoutRes int childLayout) {
        this.context = context;
        this.datas = list != null ? list : new ArrayList<GROUP>();
        this.groupLayout = groupLayout;
        this.childLayout = childLayout;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return datas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return datas.get(groupPosition).getChildCount();
    }

    @Override
    public GROUP getGroup(int groupPosition) {
        return datas.get(groupPosition);
    }

    @Override
    public CHILD getChild(int groupPosition, int childPosition) {
        return datas.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition * GROUP_MUT;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition * GROUP_MUT + childPosition;
    }

    private static final int GROUP_MUT = 10000;

    /***
     * @return {group, child}
     * */
    public static int[] getGroupChildIndex(long childId) {
        int child = (int) (childId % GROUP_MUT);
        int group = (int) (childId / GROUP_MUT);
        return new int[]{group, child};
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(groupLayout, parent, false);
        }
        updateGroupView(getGroup(groupPosition), groupPosition, isExpanded, convertView, parent);
        return convertView;
    }

    public abstract void updateGroupView(GROUP group, int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(childLayout, parent, false);
        }
        updateChildView(getChild(groupPosition, childPosition), groupPosition, childPosition, isLastChild, convertView, parent);
        return convertView;
    }

    public abstract void updateChildView(CHILD child, int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
