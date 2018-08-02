package com.dhy.xintent.preferences;

import android.support.annotation.NonNull;

import java.util.List;

public interface ObjectConverter {
    String objectToString(@NonNull Object obj);

    <V> V string2object(@NonNull String string, Class<V> dataClass);

    @NonNull
    <V> List<V> string2listObject(@NonNull String string, Class<V> dataClass);
}
