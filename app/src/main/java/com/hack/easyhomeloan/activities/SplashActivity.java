package com.hack.easyhomeloan.activities;

import android.os.Handler;

import androidx.databinding.ViewDataBinding;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.activities.login.LoginActivity;
import com.hack.easyhomeloan.base.BaseActivity;
import com.hack.easyhomeloan.utilities.AppUtils;

public class SplashActivity extends BaseActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();
    private final static int SPLASH_DISPLAY_LENGTH = 2500; //InMilliSecond

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_splash;
    }


    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                onNavigateActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void onNavigateActivity() {
        AppUtils.navigateToActivity(
                this,
                LoginActivity.class,
                null,
                true
        );
           // Navigator.launchActivityWithFinish(this, IntentHelper.getInstance().newLoginIntent(this));

    }

    @Override
    public void onBackPressed() {
        super.finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.backslide_right_out);
    }


}
