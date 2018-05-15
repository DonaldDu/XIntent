package com.dhy.xintent.preferences;

public interface ObjectConverter {
    String objectToString(Object obj);

    <V> V string2object(String string, Class<V> dataClass);
}
