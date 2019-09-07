package com.hack.easyhomeloan.application;

import android.content.Context;
import android.os.Process;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.hack.easyhomeloan.activities.home.banner.BannerImageLoadingService;
import com.hack.easyhomeloan.utilities.AppUtils;

import ss.com.bannerslider.Slider;


public class EaseHomeLoanApplication extends MultiDexApplication {
    private static EaseHomeLoanApplication mEaseHomeLoanApp;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static EaseHomeLoanApplication getInstance() {
        return mEaseHomeLoanApp;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        mEaseHomeLoanApp = this;
        super.onCreate();
        isApplicationInstalled();
        init();
    }

    private void init() {
        AppUtils.scheduleNetworkChangeJob(this);
        Slider.init(new BannerImageLoadingService(this));

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
