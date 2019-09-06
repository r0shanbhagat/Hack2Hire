package com.example.myapplication.network;


import com.example.myapplication.Hero;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {


    @GET( "marvel")
    Observable<Hero> getCityList(@Body Map<String, Map> mParams);

    @GET("marvel")
    Observable<List<Hero>> getHeroes();
}