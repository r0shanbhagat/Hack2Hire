package com.hack.easyhomeloan.preference;

import android.content.Context;

import com.hack.easyhomeloan.application.EaseHomeLoanApplication;
import com.hack.easyhomeloan.base.BasePreference;

public class AppPreferences extends BasePreference {
    private static AppPreferences appPreferences;
    private final String USER_EMAIL_KEY = "userEmail";
    private final String IS_LOGIN_KEY = "isLogin";

    protected AppPreferences(Context mContext) {
        super(mContext);
    }

    public static AppPreferences getInstance() {
        if (null == appPreferences) {
            appPreferences = new AppPreferences(EaseHomeLoanApplication.getInstance());

        }
        return appPreferences;
    }

    public void setUserLogin(boolean isLogin) {
       setBooleanValue(isLogin,IS_LOGIN_KEY);
    }

    public boolean isUserLoggedIn() {
        return getBooleanValue(IS_LOGIN_KEY);
    }

}
