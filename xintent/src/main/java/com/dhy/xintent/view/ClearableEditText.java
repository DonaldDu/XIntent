package com.dhy.xintent.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * use  DrawableRight as clearable icon
 */
public class ClearableEditText extends androidx.appcompat.widget.AppCompatEditText {
    private Drawable mClearDrawable;
    private boolean mIsClearVisible = true;

    public ClearableEditText(Context context) {
        super(context);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Drawable[] drawables = getCompoundDrawables();
        mClearDrawable = drawables[2]; // ;
        setClearDrawableVisible(false);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setClearDrawableVisible(length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getError() == null && mIsClearVisible && event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            if (x >= getWidth() - getTotalPaddingRight() && x <= getWidth() - getPaddingRight()) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    public void setClearDrawableVisible(boolean isVisible) {
        if (mIsClearVisible == isVisible) return;
        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawables(drawables[0], drawables[1], isVisible ? mClearDrawable : null, drawables[3]);
        mIsClearVisible = isVisible;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (getError() == null) {
            if (focused) {
                setClearDrawableVisible(length() > 0);
            } else {
                setClearDrawableVisible(false);
            }
        }
    }
}

