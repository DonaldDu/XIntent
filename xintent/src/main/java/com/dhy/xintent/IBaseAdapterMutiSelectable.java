package com.dhy.xintent;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public abstract class IBaseAdapterMutiSelectable<ITEM extends Selectable> extends IBaseAdapter<ITEM> {
    public IBaseAdapterMutiSelectable(Context context, @Nullable List<ITEM> list, @LayoutRes int layoutId) {
        super(context, list, layoutId);
    }

    public void selectAll(Boolean select) {
        for (ITEM item : getDatas()) {
            item.setChecked(select);
        }
        notifyDataSetChanged();
    }

    public List<ITEM> getSelectedItems() {
        List<ITEM> selected = new ArrayList<>();
        for (ITEM item : getDatas()) {
            if (item.isChecked() == Boolean.TRUE) selected.add(item);
        }
        return selected;
    }

    public void toggleSelectedStatus(ITEM item) {
        Boolean checked = item.isChecked();
        if (checked != null) {
            item.setChecked(!checked);
        }
    }

    /**
     * @param checked null:setEnabled(false); else setEnabled(true) & setChecked(checked)
     */
    protected void initSelectorStatus(View convertView, @IdRes int checkBoxId, @Nullable Boolean checked) {
        CheckBox status = convertView.findViewById(checkBoxId);
        if (checked == null) {
            status.setEnabled(false);
        } else {
            status.setEnabled(true);
            status.setChecked(checked);
        }
    }

    /**
     * @param checked null:setEnabled(false); else setEnabled(true) & setChecked(checked)
     */
    protected void initSelectorStatus(View convertView, @IdRes int checkBoxId, int visibility, @Nullable Boolean checked) {
        CheckBox status = convertView.findViewById(checkBoxId);
        status.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            if (checked == null) {
                status.setEnabled(false);
            } else {
                status.setEnabled(true);
                status.setChecked(checked);
            }
        }
    }
}
