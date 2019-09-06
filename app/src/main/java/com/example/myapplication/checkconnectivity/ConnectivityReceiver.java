package com.example.myapplication.checkconnectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.utilities.AppUtils;
/**
 * This class ensure the network connection changes and notify to the respective callbacks.
 */

public class ConnectivityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        AppUtils.showLog("ROSHAN", "NETWORK STATE" + AppUtils.isNetworkAvailable(context));
    }


}
