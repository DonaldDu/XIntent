package com.dhy.xintent;

import android.support.annotation.Nullable;

public abstract class AsyncRunnable implements Runnable {
    Async async;

    void init(Async async) {
        this.async = async;
    }

    /**
     * MUST INVOKE THIS on the end of task when SUCCESS,<br>
     * end with ONFINISH_HANDLER(set with {@link Async#onFinish(AsyncRunnable)}) if no error
     *
     * @param result current runable result
     */
    protected void next(@Nullable Object result) {
        async.next(result);
    }

    /**
     * MUST INVOKE THIS on the end of task when ERROR<br>
     * exit all task on error, end with ERROR_HANDLER(set with {@link Async#onError(AsyncRunnable)})
     *
     * @param result current runable result
     */
    protected void error(@Nullable Object result) {
        async.error(result);
    }

    /**
     * @param step the flow step
     * @return the success or error result
     */
    protected Object getResultData(int step) {
        if (step < async.datas.size()) {
            return async.datas.get(step);
        } else return null;
    }

    /**
     * @return the success or error result
     */
    protected <T> T getResultData(Class<T> cls) {
        for (Object data : async.datas) {
            if (cls.isInstance(data)) {
                return cls.cast(data);
            }
        }
        return null;
    }
}
