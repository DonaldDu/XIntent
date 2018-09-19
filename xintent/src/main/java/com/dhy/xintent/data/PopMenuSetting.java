package com.dhy.xintent.data;

import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.dhy.xintent.R;

public class PopMenuSetting {
    public final int layoutId;
    public final int itemLayoutId;
    public final int containerId;
    @Nullable
    public final Integer bg;
    public final int popWidth;
    public final Rect padding;

    public PopMenuSetting(int layoutId, int itemLayoutId, int containerId, @Nullable Integer bg, int popWidth, Rect padding) {
        this.bg = bg;
        this.popWidth = popWidth;
        this.layoutId = layoutId;
        this.containerId = containerId;
        this.itemLayoutId = itemLayoutId;
        this.padding = padding;
    }

    public static class Builder {
        private int layoutId = R.layout.xintent_pop_menu;
        private int itemLayoutId = R.layout.xintent_pop_item;
        private int containerId = R.id.container;
        private int popWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        private Integer bg;
        private Rect padding;

        public Builder layoutId(@LayoutRes int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public Builder containerId(@IdRes int containerId) {
            this.containerId = containerId;
            return this;
        }

        public Builder bg(@DrawableRes Integer bg) {
            this.bg = bg;
            return this;
        }

        public Builder popWidth(@DrawableRes int popWidth) {
            this.popWidth = popWidth;
            return this;
        }

        public Builder itemLayoutId(@DrawableRes int itemLayoutId) {
            this.itemLayoutId = itemLayoutId;
            return this;
        }

        public Builder padding(int left, int top, int right, int bottom) {
            this.padding = new Rect(left, top, right, bottom);
            return this;
        }

        public PopMenuSetting build() {
            return new PopMenuSetting(layoutId, itemLayoutId, containerId, bg, popWidth, padding);
        }
    }
}
