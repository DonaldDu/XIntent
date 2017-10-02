package com.dhy.xintent.async;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Async {
    @NonNull
    private final List<AsyncRunnable> todos = new ArrayList<>();
    @NonNull
    final List datas = new ArrayList();

    private Async() {
    }

    /**
     * @param context for runOnUiThread, set null if no need
     */
    public static Async todo(@Nullable Context context, AsyncRunnable runnable) {
        Async async = new Async();
        async.context = context;
        return async.todo(runnable);
    }

    public Async todo(AsyncRunnable runnable) {
        todos.add(runnable);
        return this;
    }

    void next(@Nullable Object obj) {
        datas.add(obj);
        next();
    }


    protected void error(@Nullable Object obj) {
        datas.add(obj);
        run(onError);
    }

    private void next() {
        if (!todos.isEmpty()) {
            run(todos.remove(0));
        } else run(onFinish);
    }

    private void run(AsyncRunnable runnable) {
        runnable.init(this);
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(runnable);
        } else runnable.run();
    }

    private AsyncRunnable onError, onFinish;
    @Nullable
    private Context context;

    /**
     * on the end of each task, MUST INVOKE {@link AsyncRunnable#next(Object)} when SUCCESS,<br>
     * or {@link AsyncRunnable#error(Object)}  when ERROR<br>
     * if {@link AsyncRunnable#error(Object)} called, finish by ERROR_HANDLER(set with {@link #onError(AsyncRunnable)})<br>
     * else finish by ONFINISH_HANDLER for {@link AsyncRunnable#next(Object)}
     */
    public void onFinish(@NonNull AsyncRunnable onFinish) {
        this.onFinish = onFinish;
        next();
    }

    /**
     * set handler for {@link AsyncRunnable#error(Object)}
     */
    public Async onError(@NonNull AsyncRunnable onError) {
        this.onError = onError;
        return this;
    }
}
