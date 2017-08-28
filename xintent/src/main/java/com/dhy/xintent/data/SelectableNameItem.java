package com.dhy.xintent.data;

import com.dhy.xintent.interfaces.SelectableName;

import java.util.ArrayList;
import java.util.List;

public class SelectableNameItem implements SelectableName {
    private String name;

    public SelectableNameItem(String name) {
        this.name = name;
    }

    private boolean checked;

    @Override
    public Boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String getName() {
        return name;
    }

    public static List<SelectableNameItem> create(String... names) {
        List<SelectableNameItem> list = new ArrayList<>();
        for (String name : names) {
            list.add(new SelectableNameItem(name));
        }
        return list;
    }
}
