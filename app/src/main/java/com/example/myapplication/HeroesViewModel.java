/*
package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.network.ApiService;
import com.example.myapplication.network.ServiceGenerator;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
 
public class HeroesViewModel extends ViewModel {


    public void getCityList(Context context, IViewCallback<CityListViewModel> pViewCallback) {
       ApiService apiService = new ServiceGenerator.Builder(context).create();
        HashMap hashMap = new HashMap<String, Object>();
        Observable observable = apiService.getCityList(CommonFrameworkUtil.getRequestParamMap(hashMap))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        observable.subscribe(new BaseNetworkObservable<CityResponseData>() {
            @Override
            public void success(CityResponseData cityResponseData) {
                if (cityResponseData != null) {
                    CityListViewModel model = new CityMapper().toViewModel(cityResponseData);
                    pViewCallback.checkAndUpdate(model);
                }
            }

            @Override
            public void failure(Throwable error) {
                pViewCallback.onFailure(error);
            }
        });
    }

    //this is the data that we will fetch asynchronously 
    private MutableLiveData<List<Hero>> heroList;
 
    //we will call this method to get the data
    public LiveData<List<Hero>> getHeroes() {
        //if the list is null 
        if (heroList == null) {
            heroList = new MutableLiveData<List<Hero>>();
            //we will load it asynchronously from server in this method
            loadHeroes();
        }
        
        //finally we will return the list
        return heroList;
    }
 
    
    //This method is using Retrofit to get the JSON data from URL 
    private void loadHeroes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
 
        Api api = retrofit.create(Api.class);
        Call<List<Hero>> call = api.getHeroes();
 
 
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                
                //finally we are setting the list to our MutableLiveData
                heroList.setValue(response.body());
            }
 
            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
 
            }
        });
    }
}*/
