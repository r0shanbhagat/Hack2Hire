package com.hack.easyhomeloan.network;

import android.content.Context;

import com.hack.easyhomeloan.utilities.AppUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class CacheControlInterceptor implements Interceptor {
    private static int maxCacheAgeInMinute = 5;
    private final String TAG = CacheControlInterceptor.class.getSimpleName();
    private Context mContext;
    private int maxStaleInDay = 28; // tolerate 4-weeks stale

    /**
     * @param mContext
     */
    public CacheControlInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Instantiates a new Cache control interceptor.
     *
     * @param mContext            the m context
     * @param maxCacheAgeInMinute the max cache age in minute
     * @param maxStaleInDay       the max stale
     */
    public CacheControlInterceptor(Context mContext, int maxCacheAgeInMinute, int maxStaleInDay) {
        this.mContext = mContext;
        CacheControlInterceptor.maxCacheAgeInMinute = maxCacheAgeInMinute;
        this.maxStaleInDay = maxStaleInDay;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder request = originalRequest.newBuilder();
        if (originalRequest.header("fresh") != null) {
            request.cacheControl(CacheControl.FORCE_NETWORK);
        }
        Response response = chain.proceed(request.build());
        return response.newBuilder().removeHeader("Pragma").removeHeader("Expires").removeHeader("Cache-Control").header(
                "Cache-Control", cacheResponseControl()).build();
    }

    private String cacheResponseControl() {
        String cacheHeaderValue;
        if (AppUtils.isNetworkAvailable(mContext)) {
            cacheHeaderValue = "public, max-age=" + TimeUnit.MINUTES.toSeconds(maxCacheAgeInMinute);
        } else {
            cacheHeaderValue = "public, only-if-cached, max-stale=" + TimeUnit.DAYS.toSeconds(maxStaleInDay);
        }
        return cacheHeaderValue;
    }
}