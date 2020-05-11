package com.dhy.xintent.simple;

import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;

public abstract class SimpleTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(@NonNull CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(@NonNull Editable editable) {

    }
}
