package com.dhy.xintent.preferences;

public interface JsonConverter {
    String objectToJson(Object obj);

    <V> V json2object(String json, Class<V> dataClass);
}
