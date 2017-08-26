package com.dhy.xintent;

public abstract class AsyncCallback<T> extends AsyncRunnable implements Callback<T> {
    @Override
    public void onCallback(T result) {
        next(result);
    }
}
