package com.hack.easyhomeloan.utilities;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.hack.easyhomeloan.dialog.LoadingDialog;

import java.lang.ref.WeakReference;

public abstract class BaseAsynTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private IAsyncCallBack<Result> iAsyncCallBack;
    private String loaderMessage;
    private boolean isCancelable;
    private WeakReference<Context> weakReference;

    /**
     * Instantiates a new Base asyn task.
     *
     * @param context the context
     * @NonNull Context context, @NonNull String loaderMessage, boolean isCancleable
     */
    public BaseAsynTask(@NonNull Context context, IAsyncCallBack iAsyncCallBack, String loaderMessage, boolean isCancelable) {
        weakReference = new WeakReference<>(context);
        this.iAsyncCallBack = iAsyncCallBack;
        this.loaderMessage = loaderMessage;
        this.isCancelable = isCancelable;
    }

    /**
     *
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        LoadingDialog.show(weakReference.get(), loaderMessage, isCancelable);
    }

    /**
     * @param result
     */
    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        LoadingDialog.dismissDialog();
        if (null != iAsyncCallBack) {
            iAsyncCallBack.onResponse(getContext(), result);
        }
    }

    /**
     * Gets context.
     *
     * @return context
     */
    protected Context getContext() {
        return weakReference.get();
    }
}
