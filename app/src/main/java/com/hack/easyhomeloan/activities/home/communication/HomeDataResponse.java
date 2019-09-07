package com.hack.easyhomeloan.activities.home.communication;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hack.easyhomeloan.base.BaseResponse;

import java.util.List;

public class HomeDataResponse extends BaseResponse {
    @SerializedName("topBanner")
    @Expose
    private List<String> topBanner = null;
    @SerializedName("userInformation")
    @Expose
    private UserInformation userInformation;
    @SerializedName("RecommendationBanner")
    @Expose
    private List<BannerData> recommendationBanner = null;


    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public List<String> getTopBanner() {
        return topBanner;
    }

    public void setTopBanner(List<String> topBanner) {
        this.topBanner = topBanner;
    }

    public List<BannerData> getRecommendationBanner() {
        return recommendationBanner;
    }

    public void setRecommendationBanner(List<BannerData> recommendationBanner) {
        this.recommendationBanner = recommendationBanner;
    }
}

