package com.example.myapplication.activities.home.viewmodel;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Hero;
import com.example.myapplication.base.BaseViewModel;
import com.example.myapplication.network.ApiService;
import com.example.myapplication.network.BaseNetworkObservable;
import com.example.myapplication.network.ServiceGenerator;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel {
    public MutableLiveData apiResponse = new MutableLiveData();
    public MutableLiveData viewClick = new MutableLiveData<Integer>();
    private Context mContext;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mContext=application.getApplicationContext();
    }
    public void getCityList() {
        ApiService apiService = new ServiceGenerator.Builder(null).create();
       // AppUtils.getRequestParamMap(hashMap)
        HashMap hashMap = new HashMap<String, Object>();
        Observable observable = apiService.getHeroes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        observable.subscribe(new BaseNetworkObservable<List<Hero>>() {
            @Override
            public void success(List<Hero> cityResponseData) {
                if (cityResponseData != null) {
                    apiResponse.setValue(cityResponseData);
                }
            }

            @Override
            public void failure(Throwable error) {
                apiResponse.setValue(error);
            }
        });
    }

}