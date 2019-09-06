package com.example.myapplication.network;


import com.example.myapplication.dialog.LoadingDialog;
import com.example.myapplication.utilities.AppUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseNetworkObservable<T> implements Observer<T> {
    private static final String TAG = BaseNetworkObservable.class.getSimpleName();

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
        if (null != t) {
            success(t);
        } else {
            failure(new RuntimeException("Unexpected response " + t));
        }
    }

    @Override
    public void onError(Throwable t) {
        failure(t);
        AppUtils.showException(TAG, t);
        LoadingDialog.dismissDialog();
    }

    @Override
    public void onComplete() {
    }

    public abstract void success(T response);

    public abstract void failure(Throwable t);
}
