package com.hack.easyhomeloan.activities.detail.viewmodel;


import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hack.easyhomeloan.base.BaseViewModel;

public class DetailViewModel extends BaseViewModel {
    public MutableLiveData apiResponse = new MutableLiveData();
    public MutableLiveData viewClick = new MutableLiveData<Integer>();


    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void onViewClicks(View view) {
        viewClick.setValue(view.getId());
    }

}