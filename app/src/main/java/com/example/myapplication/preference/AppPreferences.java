package com.example.myapplication.preference;

import android.content.Context;

import com.example.myapplication.application.ExampleApplication;
import com.example.myapplication.base.BasePreference;

public class AppPreferences extends BasePreference {
    private static AppPreferences appPreferences;
    private final String USER_EMAIL_KEY = "userEmail";
    private final String IS_LOGIN_KEY = "isLogin";

    protected AppPreferences(Context mContext) {
        super(mContext);
    }

    public static AppPreferences getInstance() {
        if (null == appPreferences) {
            appPreferences = new AppPreferences(ExampleApplication.getInstance());

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
