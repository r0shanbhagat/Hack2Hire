package com.example.myapplication.base;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * BasePreference Class :
 *
 * @author Roshan Kumar
 * @version 3.0
 * @since 14/3/19
 */
public abstract class BasePreference {

    private static final String APP_PREF_FILE = "app_pref.xml";
    private static SharedPreferences sharedPreferences;

    protected BasePreference(Context mContext){
        sharedPreferences =
                mContext
                        .getSharedPreferences(APP_PREF_FILE, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    public void setStringValue(String value, String key) {
        getEditor()
                .putString(key, value)
                .apply();
    }

    public String getStringValue(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setIntValue(int value, String key) {
        getEditor()
                .putInt(key, value)
                .apply();
    }

    public int getIntValue(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public boolean getBooleanValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void setBooleanValue(boolean value,String key) {
        getEditor()
                .putBoolean(key, value)
                .apply();
    }

    public void clearPreferences() {
        getEditor()
                .clear()
                .apply();
    }

}



