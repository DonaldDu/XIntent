package com.dhy.xintent.adapter;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public abstract class IPopMenuAdapter<T> {
    private List<T> list;

    public IPopMenuAdapter(List<T> list) {
        this.list = list != null ? list : new ArrayList<T>();
    }

    public T getItemData(int position) {
        return list.get(position);
    }

    public int getCount() {
        return list.size();
    }

    public abstract String getItemText(int position);

    public void onUpdateMenu(View view, String menu) {
        if (view instanceof TextView) {
            ((TextView) view).setText(menu);
        }
    }

    public abstract void onItemSelected(T itemData, int position);

    public void onItemPrepared(int position, View view) {

    }

    public boolean isValid(int position) {
        return true;
    }
}
