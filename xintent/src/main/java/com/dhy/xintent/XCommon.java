package com.dhy.xintent;

import android.app.Dialog;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.dhy.xintent.annotation.Visibility;
import com.dhy.xintent.interfaces.IFindViewById;

public class XCommon extends XCommonBase {
    //region setTextWithFormat

    public static TextView setTextWithFormat(Dialog container, @IdRes int rid, Object value, final Boolean show) {
        return setTextWithFormat((TextView) container.findViewById(rid), value, show);
    }

    public static TextView setTextWithFormat(View container, @IdRes int rid, Object value, final Boolean show) {
        return setTextWithFormat((TextView) container.findViewById(rid), value, show);
    }

    public static TextView setTextWithFormat(IFindViewById container, @IdRes int rid, Object value, final Boolean show) {
        return setTextWithFormat((TextView) container.findViewById(rid), value, show);
    }

    public static TextView setTextWithFormat(Dialog container, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setTextWithFormat((TextView) container.findViewById(rid), value, visibility);
    }

    public static TextView setTextWithFormat(View container, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setTextWithFormat((TextView) container.findViewById(rid), value, visibility);
    }

    public static TextView setTextWithFormat(IFindViewById container, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setTextWithFormat((TextView) container.findViewById(rid), value, visibility);
    }

    public static TextView setTextWithFormat(Dialog container, @IdRes int rid, Object value) {
        return setTextWithFormat((TextView) container.findViewById(rid), value);
    }

    public static TextView setTextWithFormat(View container, @IdRes int rid, Object value) {
        return setTextWithFormat((TextView) container.findViewById(rid), value);
    }

    public static TextView setTextWithFormat(IFindViewById container, @IdRes int rid, Object value) {
        return setTextWithFormat((TextView) container.findViewById(rid), value);
    }
    //endregion
    //region setText

    public static TextView setText(Dialog container, @IdRes int rid, Object value, @Nullable Boolean show) {
        return setText((TextView) container.findViewById(rid), value, show);
    }

    public static TextView setText(View container, @IdRes int rid, Object value, @Nullable Boolean show) {
        return setText((TextView) container.findViewById(rid), value, show);
    }

    public static TextView setText(IFindViewById container, @IdRes int rid, Object value, @Nullable Boolean show) {
        return setText((TextView) container.findViewById(rid), value, show);
    }

    public static TextView setText(Dialog container, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setText((TextView) container.findViewById(rid), value, visibility);
    }

    public static TextView setText(View container, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setText((TextView) container.findViewById(rid), value, visibility);
    }

    public static TextView setText(IFindViewById container, @IdRes int rid, Object value, @Visibility @Nullable final Integer visibility) {
        return setText((TextView) container.findViewById(rid), value, visibility);
    }

    @Nullable
    public static TextView setText(Dialog container, @IdRes int rid, Object value) {
        return setText((TextView) container.findViewById(rid), value);
    }

    @Nullable
    public static TextView setText(View container, @IdRes int rid, Object value) {
        return setText((TextView) container.findViewById(rid), value);
    }

    @Nullable
    public static TextView setText(IFindViewById container, @IdRes int rid, Object value) {
        return setText((TextView) container.findViewById(rid), value);
    }
    //endregion
    //region setImage

    public static ImageView setImage(Dialog container, @IdRes int rid, @DrawableRes int image, @Nullable final Boolean show) {
        return setImage((ImageView) container.findViewById(rid), image, show);
    }

    public static ImageView setImage(View container, @IdRes int rid, @DrawableRes int image, @Nullable final Boolean show) {
        return setImage((ImageView) container.findViewById(rid), image, show);
    }

    public static ImageView setImage(IFindViewById container, @IdRes int rid, @DrawableRes int image, @Nullable final Boolean show) {
        return setImage((ImageView) container.findViewById(rid), image, show);
    }

    public static ImageView setImage(Dialog container, @IdRes int rid, @DrawableRes int image, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) container.findViewById(rid), image, visibility);
    }

    public static ImageView setImage(View container, @IdRes int rid, @DrawableRes int image, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) container.findViewById(rid), image, visibility);
    }

    public static ImageView setImage(IFindViewById container, @IdRes int rid, @DrawableRes int image, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) container.findViewById(rid), image, visibility);
    }

    public static ImageView setImage(Dialog container, @IdRes int rid, @DrawableRes int image) {
        return setImage((ImageView) container.findViewById(rid), image);
    }

    public static ImageView setImage(View container, @IdRes int rid, @DrawableRes int image) {
        return setImage((ImageView) container.findViewById(rid), image);
    }

    public static ImageView setImage(IFindViewById container, @IdRes int rid, @DrawableRes int image) {
        return setImage((ImageView) container.findViewById(rid), image);
    }

    public static ImageView setImage(Dialog container, @IdRes int rid, Uri uri, @Nullable final Boolean show) {
        return setImage((ImageView) container.findViewById(rid), uri, show);
    }

    public static ImageView setImage(View container, @IdRes int rid, Uri uri, @Nullable final Boolean show) {
        return setImage((ImageView) container.findViewById(rid), uri, show);
    }

    public static ImageView setImage(IFindViewById container, @IdRes int rid, Uri uri, @Nullable final Boolean show) {
        return setImage((ImageView) container.findViewById(rid), uri, show);
    }

    public static ImageView setImage(Dialog container, @IdRes int rid, Uri uri, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) container.findViewById(rid), uri, visibility);
    }

    public static ImageView setImage(View container, @IdRes int rid, Uri uri, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) container.findViewById(rid), uri, visibility);
    }

    public static ImageView setImage(IFindViewById container, @IdRes int rid, Uri uri, @Visibility @Nullable final Integer visibility) {
        return setImage((ImageView) container.findViewById(rid), uri, visibility);
    }

    public static ImageView setImage(Dialog container, @IdRes int rid, Uri uri) {
        return setImage((ImageView) container.findViewById(rid), uri);
    }

    public static ImageView setImage(View container, @IdRes int rid, Uri uri) {
        return setImage((ImageView) container.findViewById(rid), uri);
    }

    public static ImageView setImage(IFindViewById container, @IdRes int rid, Uri uri) {
        return setImage((ImageView) container.findViewById(rid), uri);
    }
    //endregion
}
