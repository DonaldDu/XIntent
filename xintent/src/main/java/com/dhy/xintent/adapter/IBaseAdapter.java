package com.dhy.xintent.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dhy.xintent.interfaces.IFindViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class IBaseAdapter<ITEM> extends BaseAdapter implements IFindViewById {
    private List<ITEM> list;
    protected final int layoutId;
    protected View currentConvertView;
    protected final LayoutInflater inflater;

    public IBaseAdapter(Context context, @Nullable List<ITEM> list, @LayoutRes int layoutId) {
        inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
        setDatas(list);
    }

    public IBaseAdapter<ITEM> setDatas(@Nullable ITEM[] datas) {
        list = new ArrayList<>();
        if (datas != null) {
            list.addAll(Arrays.asList(datas));
        }
        return this;
    }

    public IBaseAdapter<ITEM> setDatas(@Nullable List<ITEM> list) {
        this.list = list != null ? list : new ArrayList<ITEM>();
        return this;
    }

    @NonNull
    public List<ITEM> getDatas() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ITEM getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View findViewById(@IdRes int id) {
        return currentConvertView.findViewById(id);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layoutId, parent, false);
        }
        currentConvertView = convertView;
        updateItemView(getItem(position), position, convertView, parent);
        return convertView;
    }

    public abstract void updateItemView(ITEM i, int position, View convertView, ViewGroup parent);

    public void notifyDataSetChanged(@Nullable List<ITEM> list) {
        getDatas().clear();
        if (list != null) getDatas().addAll(list);
        notifyDataSetChanged();
    }
}

