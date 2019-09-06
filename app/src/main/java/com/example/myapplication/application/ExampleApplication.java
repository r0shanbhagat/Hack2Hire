package com.example.myapplication.application;

import android.content.Context;
import android.os.Process;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.bumptech.glide.request.target.ViewTarget;
import com.example.myapplication.R;
import com.example.myapplication.utilities.AppUtils;


public class ExampleApplication extends MultiDexApplication {
    private static ExampleApplication mExampleApp;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static ExampleApplication getInstance() {
        return mExampleApp;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        mExampleApp = this;
        super.onCreate();
        isApplicationInstalled();
        init();
    }

    private void init() {
        AppUtils.scheduleNetworkChangeJob(this);

    }

    /**
     * Checks if application installed properly or not.
     */
    private void isApplicationInstalled() {
        if (getResources() == null) {
            Process.killProcess(Process.myPid());
        }
    }


}
