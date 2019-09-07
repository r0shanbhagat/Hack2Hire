package com.hack.easyhomeloan.network;

import android.content.Context;
import android.text.TextUtils;

import com.hack.easyhomeloan.BuildConfig;
import com.hack.easyhomeloan.utilities.AppUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String TAG = ServiceGenerator.class.getSimpleName();
    private final String baseUrl;
    private final Map headerMap;
    private final int defaultTimeOutInMinute;
    private final int maxRetryCount;
    private final Context context;
    private final String TEMP_URL = "http://www.emptyurl.com";
    private final boolean isCacheEnable;
    private Retrofit retrofit;

    private ServiceGenerator(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.headerMap = builder.headerMap;
        this.defaultTimeOutInMinute = builder.defaultTimeOutInMinute;
        this.maxRetryCount = builder.maxRetryCount;
        this.isCacheEnable = builder.isCacheEnable;
        this.context = builder.context;
        setupClient();
    }


    /**
     * Create service s.
     *
     * @param <S>          the type parameter
     * @param serviceClass the service class
     * @return s s
     */
    public <S> S createService(Class<S> serviceClass) {
        if (null == retrofit) {
            return null;
        }
        return retrofit.create(serviceClass);
    }

    /**
     *
     */
    private void setupClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttpClient())
                .build();
    }

    /**
     * @return @method use for provide ok Http client
     */
    private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new HttpInterceptor(maxRetryCount, headerMap));
        if (isCacheEnable) {
            try {
                final long sizeOfCacheInMB = 5;
                File httpCacheDirectory = new File(context.getExternalCacheDir(), ".responses");
                Cache cache = new Cache(httpCacheDirectory, sizeOfCacheInMB * 1024 * 1024);
                okHttpClientBuilder.cache(cache);
                okHttpClientBuilder.addNetworkInterceptor(new CacheControlInterceptor(context));
            } catch (Exception e) {
                AppUtils.showException(TAG, e);
            }
        }
        okHttpClientBuilder.connectTimeout(defaultTimeOutInMinute, TimeUnit.SECONDS)
                .readTimeout(defaultTimeOutInMinute, TimeUnit.SECONDS)
                .writeTimeout(defaultTimeOutInMinute, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return okHttpClientBuilder.build();
    }


    public static class Builder {
        private final Context context;
        private String baseUrl= BuildConfig.BASE_URL;
        private Map headerMap;
        private int defaultTimeOutInMinute = 2;
        private int maxRetryCount = 3;
        private boolean isCacheEnable = false;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setDefaultTimeOutInMinute(int defaultTimeOutInMinute) {
            this.defaultTimeOutInMinute = defaultTimeOutInMinute;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setMaxRetryCount(int maxRetryCount) {
            this.maxRetryCount = maxRetryCount;
            return this;
        }

        public Builder setCacheEnable(boolean cacheEnable) {
            isCacheEnable = cacheEnable;
            return this;
        }

        public Builder setAuthorizationHeader(Map headerMap) {
            this.headerMap = headerMap;
            return this;
        }

        /**
         * Can be used for API Service
         * @return
         */
        public ApiService create() {
            ServiceGenerator serviceGenerator = new ServiceGenerator(this);
            if (TextUtils.isEmpty(serviceGenerator.baseUrl)) {
                AppUtils.showException(TAG, new NullPointerException("Base url empty"));
            }
            return serviceGenerator.createService(ApiService.class);
        }

    }
}
