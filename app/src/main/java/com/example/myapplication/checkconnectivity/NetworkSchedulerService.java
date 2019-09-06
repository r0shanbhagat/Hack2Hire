package com.example.myapplication.checkconnectivity;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Service to handle callbacks from the JobScheduler. Requests scheduled with the JobScheduler
 * ultimately land on this service's "onStartJob" method.
 *
 * @author jiteshmohite
 */
public class NetworkSchedulerService extends JobService {

    private static final String TAG = NetworkSchedulerService.class.getSimpleName();
    private final String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private ConnectivityReceiver mConnectivityReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mConnectivityReceiver = new ConnectivityReceiver();
    }


    /**
     * When the app's HomeActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        registerReceiver(mConnectivityReceiver, new IntentFilter(CONNECTIVITY_ACTION));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        unregisterReceiver(mConnectivityReceiver);
        return true;
    }

}
