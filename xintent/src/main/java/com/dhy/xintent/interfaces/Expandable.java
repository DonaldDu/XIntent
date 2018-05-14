package com.dhy.xintent.interfaces;

import java.util.List;

public interface Expandable<Child> {
    int getChildCount();

    List<Child> getChildren();

    void setChildren(List<Child> children);
}