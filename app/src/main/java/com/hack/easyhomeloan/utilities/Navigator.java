package com.hack.easyhomeloan.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.hack.easyhomeloan.R;


/*
#Ref For Clear All Activity:https://tips.androidhive.info/2013/10/how-to-clear-all-activity-stack-in-android/
 */
public final class Navigator {
    private Navigator() {
    }

    public static void launchActivity(Context context, Intent intent) {
        if (!AppUtils.isActivityRunning(context, intent)) {
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.backslide_right_out);
        }
    }

    public static void launchActivityForceFully(Context context, Intent intent) {
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.backslide_right_out);
    }

    public static void launchActivityWithResult(Activity activity, int requestCode, Intent intent) {
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.backslide_right_out);
    }

    public static void launchActivityWithResult(Fragment activity, int requestCode, Intent intent) {
        activity.startActivityForResult(intent, requestCode);
        activity.getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.backslide_right_out);
    }

    public static void launchActivityWithFinish(Context context, Intent intent) {
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.backslide_right_out);
        ((Activity) context).finish();
    }

    public static void launchActivityWithFinishAll(Context context, Intent intent) {
        if (!AppUtils.isActivityRunning(context, intent)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.backslide_right_out);
        }
    }
}
