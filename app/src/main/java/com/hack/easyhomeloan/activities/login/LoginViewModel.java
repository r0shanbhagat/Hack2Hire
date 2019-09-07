package com.hack.easyhomeloan.activities.login;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.hack.easyhomeloan.base.BaseResponse;
import com.hack.easyhomeloan.base.BaseViewModel;
import com.hack.easyhomeloan.network.ApiService;
import com.hack.easyhomeloan.network.BaseNetworkObservable;
import com.hack.easyhomeloan.network.ServiceGenerator;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {
    public ObservableField userId = new ObservableField<String>();
    MutableLiveData viewClick=new MutableLiveData<Integer>();
    public ObservableField errorText = new ObservableField<String>();
    MutableLiveData apiResponse = new MutableLiveData();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
   public void onViewClicks(View view) {
        viewClick.setValue(view.getId());
    }

    /**
     * @param userId
     */
    void doCheckEligibility(String userId) {
        ApiService apiService = new ServiceGenerator.Builder(null).create();
        Observable observable = apiService.checkEligibility(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        observable.subscribe(new BaseNetworkObservable<BaseResponse>() {
            @Override
            public void success(BaseResponse response) {
                apiResponse.setValue(response);
            }

            @Override
            public void failure(Throwable error) {
                apiResponse.setValue(error);
            }
        });
    }

}
