package com.dhy.xintent;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A new layout support set size with based size.
 * <p>set with joson like string in view's contentDescription.</p>
 * <p>eg: "bw:iw,bh:ih-0.5iw","w:w,h:-w+15dp-12px"</p>
 * <p>iw: self measured width, ih self measured height.</p>
 * <p>w:based width, h:based height.</p>
 */
public class BasedSizeLayout extends RelativeLayout {

    public BasedSizeLayout(Context context) {
        super(context);
    }

    public BasedSizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (settings.isEmpty()) {
            readSettingTask(this);
            initBaseSizeTask();
            useSetting(this);
        }
    }

    void readSettingTask(ViewGroup viewGroup) {
        readSetting(viewGroup);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                readSettingTask((ViewGroup) view);
            } else readSetting(view);
        }
    }

    void readSetting(View view) {//"bw:1iw,bh:1ih,w:w,w:h,h:w,h:h"
        CharSequence description = view.getContentDescription();
        String settingString = null;
        if (description != null) {
            settingString = description.toString().replaceAll(" ", "");
        }
        if (TextUtils.isEmpty(settingString) || settings.get(view) != null) return;

        String[] attrs = settingString.split(",");
        settings.put(view, attrs);
    }

    void initBaseSizeTask() {
        datas.put("px", (float) 1);
        datas.put("dp", getContext().getResources().getDisplayMetrics().density);
        datas.put("w", 0f);
        datas.put("h", 0f);
        initBaseSize();
        initBaseSize();
    }

    void initBaseSize() {
        String[] baseNames = {"bw", "bh"};
        for (String baseName : baseNames) {
            String type = baseName.substring(1);
            if (datas.get(type) == 0) {
                View view = findViewWithAttrName(baseName);
                if (view != null) {
                    String[] setting = settings.get(view);

                    String baseAttr = getAttr(setting, baseName);
                    if (baseAttr != null) {
                        float size = getSize(view, baseAttr);
                        if (size != 0) datas.put(type, size);
                    }
                }
            }
        }
    }

    View findViewWithAttrName(String name) {
        for (View v : settings.keySet()) {
            String[] attrs = settings.get(v);
            if (attrs != null) {
                String attr = getAttr(attrs, name);
                if (attr != null) return v;
            }
        }
        return null;
    }

    String getAttr(String[] attrs, String name) {
        for (String attr : attrs) {
            if (attr.startsWith(name)) {
                return attr;
            }
        }
        return null;
    }

    void useSetting(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (settings.get(view) != null) {
                upSetting(view);
            }
            if (view instanceof ViewGroup) {
                useSetting((ViewGroup) view);
            }
        }
    }


    void upSetting(View view) {
        String[] attrs = this.settings.get(view);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = getSize(view, attrs, true);
        params.height = getSize(view, attrs, false);
        if (params.width == 0) params.width = view.getMeasuredWidth();
        if (params.height == 0) params.height = view.getMeasuredHeight();
        view.setLayoutParams(params);
    }

    Map<View, String[]> settings = new HashMap<>();

    Map<String, Float> datas = new HashMap<>();

    int getSize(View view, String[] attrs, boolean width) {
        String name = width ? "w" : "h";
        String attr = getAttr(attrs, name);
        return attr == null ? 0 : getSize(view, attr);
    }

    /**
     * @param attr "x:12w-h-4px+7dp"
     * @return the size or 0 for not prepared baseValue
     * @throws IllegalArgumentException for unkown unit
     **/
    int getSize(View view, String attr) {
        if (!attr.contains(":")) return 0;
        datas.put("iw", (float) view.getMeasuredWidth());
        datas.put("ih", (float) view.getMeasuredWidth());
        attr = attr.split(":")[1];
        float value = 0;
        Pattern pattern = Pattern.compile("[+-]?(\\d*\\.*\\d*)(\\w+)");
        Matcher matcher = pattern.matcher(attr);
        while (matcher.find()) {
            String numberUnit = matcher.group();
            String unitName = matcher.group(matcher.groupCount());
            String number = numberUnit.substring(0, numberUnit.length() - unitName.length());
            if (number.length() == 1 && (number.equals("-") || number.contains("+"))) {
                number = number + "1";
            } else if (number.length() == 0) number = "1";
            Float unit = datas.get(unitName);
            if (unit != null) {
                if (unit != 0) {
                    value += Float.parseFloat(number) * unit;
                } else return 0;
            } else {
                String msg = "unkown unit of '%1s' in '%2s', support dp & px only";
                throw new IllegalArgumentException(String.format(msg, matcher.group(2), attr));
            }
        }
        return (int) value;
    }
}
