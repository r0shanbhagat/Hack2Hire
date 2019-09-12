package com.hack.easyhomeloan.utilities;

import android.content.Context;


public interface IAsyncCallBack<T> {
    /**
     * On response.
     *
     * @param context the context
     * @param t       the t
     */
    void onResponse(Context context, T t);

    /**
     * Failure.
     *
     * @param e the e
     */
    void failure(Exception e);
}
