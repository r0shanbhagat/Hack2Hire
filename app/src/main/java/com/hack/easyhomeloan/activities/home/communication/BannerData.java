package com.hack.easyhomeloan.activities.home.communication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerData {

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
