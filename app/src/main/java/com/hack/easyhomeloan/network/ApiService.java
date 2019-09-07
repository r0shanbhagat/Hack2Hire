package com.hack.easyhomeloan.network;


import com.hack.easyhomeloan.activities.home.communication.HomeDataResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("marvel")
    Observable<HomeDataResponse> checkEligibility(@Query("userId") String userId);

    @GET("marvel1")
    Observable<HomeDataResponse> fetchHomeDashboardData(@Query("userId") String userId);
}