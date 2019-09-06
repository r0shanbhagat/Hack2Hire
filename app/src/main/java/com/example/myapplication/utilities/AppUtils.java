package com.example.myapplication.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.checkconnectivity.NetworkSchedulerService;
import com.example.myapplication.preference.AppPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type App utils.
 */
public final class AppUtils {


    /**
     * Gets active fragment.
     *
     * @param abstractAppCompactActivity the abstract base activity
     * @return active fragment
     */
    public static Fragment getActiveFragment(@NonNull BaseActivity abstractAppCompactActivity) {
        if (abstractAppCompactActivity.getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = abstractAppCompactActivity.getSupportFragmentManager().getBackStackEntryAt(abstractAppCompactActivity.getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return abstractAppCompactActivity.getSupportFragmentManager().findFragmentByTag(tag);
    }


    public static boolean isActivityRunning(Context mContext, Intent intentClass) {
        boolean status = false;
        try {
            ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            status = componentInfo.getClassName().equalsIgnoreCase(intentClass.getComponent().getClassName());
        } catch (Exception e) {
            AppUtils.showException(e);
        } finally {
            return status;
        }
    }

    public static void scheduleNetworkChangeJob(Context context) {
        JobInfo myJob = new JobInfo.Builder(0, new ComponentName(context, NetworkSchedulerService.class))
                .setRequiresCharging(true)
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(myJob);
    }


    public static void showSnackBar(Activity activity, String message) {
        Snackbar.make(activity.findViewById(android.R.id.content), message,
                Snackbar.LENGTH_SHORT).show();
    }

    public static void loadImage(@Nullable final Context mContext, @NonNull final Object arg0, @NonNull final ImageView iv) {
        try {
            Glide.with(mContext)
                    .load(arg0)
                    .into(iv);
        } catch (Exception e) {
            AppUtils.showException(e);
        }
        //.signature(new ObjectKey(System.currentTimeMillis()))

    }

    public static void loadImage(@Nullable Context mContext, @NonNull Object arg0, @NonNull ImageView iv, int defaultImageId) {
        try {
            Glide.with(mContext)
                    .load(arg0)
                    .placeholder(defaultImageId)
                    .into(iv);
        } catch (Exception e) {
            AppUtils.showException(e);
        }
    }

    public static void showException(Throwable t) {
        if (BuildConfig.LOG) {
            Log.e("", Log.getStackTraceString(t));
        }
    }


    /**
     * Show exception.You can disable Logs by setting "BuildConfig.LOG" value to False
     * <p>
     * * @param tagName     the tag name
     *
     * @param tagName the tag name
     * @param t       the t
     */
    public static void showException(String tagName, Throwable t) {
        if (BuildConfig.LOG) {
            Log.e(tagName, Log.getStackTraceString(t));
        }
    }

    /**
     * Is network available boolean.
     *
     * @param context the context
     * @return boolean boolean
     */
    public static boolean isNetworkAvailable(@NonNull Context context) {
        try {
            if (null != context) {
                return isWIFINetworkAvailable(context) || isMobileNetworkAvailable(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Is wifi network available boolean.
     *
     * @param context the context
     * @return boolean boolean
     */
    public static boolean isWIFINetworkAvailable(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
                || connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTING;
    }


    /**
     * Is mobile network available boolean.
     *
     * @param context the context
     * @return boolean boolean
     */
    public static boolean isMobileNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTING;
    }

    /**
     * Show log. You can disable Logs by setting "BuildConfig.LOG" value to False
     *
     * @param message the message
     */
    public static void showLog(String message) {
        if (BuildConfig.LOG && !TextUtils.isEmpty(message)) {
            int maxLogSize = 1000;
            for (int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                Log.v("", message.substring(start, end));
            }
        }

    }

    /**
     * Show log. You can disable Logs by setting "BuildConfig.LOG" value to False
     *
     * @param tagName the tag name
     * @param message the message
     */
    public static void showLog(String tagName, String message) {
        if (BuildConfig.LOG && !TextUtils.isEmpty(message)) {
            int maxLogSize = 1000;
            for (int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                Log.v(tagName, message.substring(start, end));
            }
        }

    }

    /**
     * Show toast message.
     *
     * @param context the m context
     * @param message the message
     */
    public static void showToastMessage(@NonNull Context context, @NonNull String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    /**
     * Clear cache data.
     *
     * @param context the context
     */
    public static void clearCacheData(Context context) {
        AppPreferences.getInstance().clearPreferences();
        // Navigator.launchActivityWithFinishAll(context, IntentHelper.getInstance().newLoginIntent(context));

    }
    @SuppressLint("HardwareIds")
   public static String getDeviceId( Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Is list not empty boolean.
     *
     * @param list the list
     * @return the boolean
     */
    public static boolean isListNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }


    /**
     *
     * @param callerActivity
     * @param targetActivity
     * @param dataBundle
     * @param isToFinishActivity
     */
    public static void navigateToActivity(
            BaseActivity callerActivity,
            Class<? extends BaseActivity> targetActivity,
            Bundle dataBundle,
            boolean isToFinishActivity
    ) {
        Intent intent = new Intent(callerActivity.getApplicationContext(), targetActivity);
        if(null!=dataBundle) {
            intent.putExtras(dataBundle);
        }
        callerActivity.startActivity(intent);
        if (isToFinishActivity) {
            callerActivity.finish();
        }
    }

    /**
     * @param mParamsData
     * @return
     */
    public static Map<String, Map> getRequestParamMap(@NonNull Map<String, Object> mParamsData) {
        Map<String, Map> mParamsRequest = new HashMap<>();
      //  mParamsRequest.put("info", getInfoRequestParamMap(""));
        mParamsRequest.put("data", mParamsData);
        return mParamsRequest;
    }


   /* public static Map<String, Object> getInfoRequestParamMap(String eCode) {
        HashMap<String, Object> mParamsInfo = new HashMap<>();
        PreferenceManager preferenceManager = BaseApplication.getPreferenceManager();
        mParamsInfo.put("deviceId", preferenceManager.getDeviceId());
        mParamsInfo.put("deviceManufacturer", preferenceManager.getDeviceManufacturer());
        mParamsInfo.put("deviceModel", preferenceManager.getDeviceModel());
        mParamsInfo.put("osVersion", preferenceManager.getOsVersion());
        mParamsInfo.put("platform", preferenceManager.getPlatform());
        mParamsInfo.put("appVersion", preferenceManager.getAppVersionName());
        mParamsInfo.put("appVersionCode", preferenceManager.getAppVersionCode());
        mParamsInfo.put("eCode", ValidationUtils.getCheckedString(eCode));
        // mParamsInfo.put("serverSyncTime", System.currentTimeMillis());
        return mParamsInfo;
    }
*/

    /**
     * Sets recycler basic properties.
     *
     * @param context      the context
     * @param recyclerView the recycler view
     */
    public static void setRecyclerBasicProperties(Context context, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    /**
     * Show hide icon on touch boolean.
     *
     * @param motionEvent        the motion event
     * @param editText           the edit text
     * @param isIconShow         the is show
     * @param typeface           the typeface
     * @param iconShowImage      the icon show image
     * @param iconShowImagefalse the icon show imagefalse
     * @return boolean boolean
     */
    public static boolean showHideIconOnTouch(@NonNull MotionEvent motionEvent, @NonNull EditText editText, boolean isIconShow, @NonNull Typeface typeface, int iconShowImage, int iconShowImagefalse) {
        final int DRAWABLE_RIGHT = 2;
        if (motionEvent.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
            if (!isIconShow) {
                isIconShow = true;
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setSelection(editText.getText().length());
                editText.setTypeface(typeface);
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconShowImagefalse, 0);
            } else {
                isIconShow = false;
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setSelection(editText.getText().length());
                editText.setTypeface(typeface);
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconShowImage, 0);
            }
        }
        return isIconShow;
    }

    /**
     * @method used to apply animation in fragment navigation
     */
    public static void applyAnimation(NavOptions.Builder navOptionBuilder) {
        navOptionBuilder.setEnterAnim(R.anim.right_in);
        navOptionBuilder.setExitAnim(R.anim.left_out);
        navOptionBuilder.setPopExitAnim(R.anim.right_out);
        navOptionBuilder.setPopEnterAnim(R.anim.left_in);
    }

    /**
     * @method used to apply animation when current screen need to be popped out
     */
    public static void applyReverseAnimation(NavOptions.Builder navOptionBuilder) {
        navOptionBuilder.setEnterAnim(R.anim.left_in);
        navOptionBuilder.setExitAnim(R.anim.left_out);
        navOptionBuilder.setPopExitAnim(R.anim.right_out);
        navOptionBuilder.setPopEnterAnim(R.anim.left_in);

    }


    /**
     * Load json from asset string.
     *
     * @param mContext    the m context
     * @param jsoFileName the jso file name
     * @return the string
     * @throws IOException the io exception
     */
    public static String loadJSONFromAsset(@NonNull Context mContext, @NonNull String jsoFileName) throws IOException {
        String json = null;
        InputStream is = mContext.getAssets().open(jsoFileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");
        return json;
    }






}
