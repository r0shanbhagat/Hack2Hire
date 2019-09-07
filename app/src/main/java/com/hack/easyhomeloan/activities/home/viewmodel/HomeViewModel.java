package com.hack.easyhomeloan.activities.home.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.hack.easyhomeloan.activities.home.communication.HomeDataResponse;
import com.hack.easyhomeloan.base.BaseViewModel;
import com.hack.easyhomeloan.network.ApiService;
import com.hack.easyhomeloan.network.BaseNetworkObservable;
import com.hack.easyhomeloan.network.ServiceGenerator;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel {
    public MutableLiveData apiResponse = new MutableLiveData();
    public MutableLiveData viewClick = new MutableLiveData<Integer>();
    public ObservableField userId = new ObservableField<String>("N/A");
    public ObservableField name = new ObservableField<String>("N/A");
    public ObservableField gender = new ObservableField<String>("N/A");
    public ObservableField address = new ObservableField<String>("N/A");

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void getHomeDashboardData(String userId) {
        ApiService apiService = new ServiceGenerator.Builder(null).create();
        // AppUtils.getRequestParamMap(hashMap)
        //  HashMap hashMap = new HashMap<String, Object>();
        Observable observable = apiService.fetchHomeDashboardData(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        observable.subscribe(new BaseNetworkObservable<HomeDataResponse>() {
            @Override
            public void success(HomeDataResponse response) {
                apiResponse.setValue(response);
            }

            @Override
            public void failure(Throwable error) {
                apiResponse.setValue(error);
            }
        });
    }

}