package com.example.myapplication.activities;

import android.os.Handler;

import androidx.databinding.ViewDataBinding;

import com.example.myapplication.R;
import com.example.myapplication.activities.home.HomeActivity;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.preference.AppPreferences;
import com.example.myapplication.utilities.AppUtils;
import com.example.myapplication.utilities.Navigator;

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
        if (AppPreferences.getInstance().isUserLoggedIn()) {
            AppUtils.navigateToActivity(
                    this,
                    HomeActivity.class,
                    null,
                    true
            );
        } else {
            AppUtils.navigateToActivity(
                    this,
                    HomeActivity.class,
                    null,
                    true
            );
           // Navigator.launchActivityWithFinish(this, IntentHelper.getInstance().newLoginIntent(this));
        }
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
