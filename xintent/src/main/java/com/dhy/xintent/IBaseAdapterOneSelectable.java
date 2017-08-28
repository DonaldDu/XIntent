package com.dhy.xintent;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import java.util.List;

public abstract class IBaseAdapterOneSelectable<ITEM extends Selectable> extends IBaseAdapter<ITEM> {
    public IBaseAdapterOneSelectable(Context context, @Nullable List<ITEM> list, @LayoutRes int layoutId) {
        super(context, list, layoutId);
    }

    public void selectIndex(int position) {
        List<ITEM> datas = getDatas();
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setChecked(i == position);
        }
    }

    @Nullable
    public ITEM getSelectedItem() {
        List<ITEM> datas = getDatas();
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).isChecked()) return datas.get(i);
        }
        return null;
    }
}
