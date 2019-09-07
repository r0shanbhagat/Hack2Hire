package com.hack.easyhomeloan.activities.home.banner;

import android.content.Context;
import android.widget.ImageView;

import com.hack.easyhomeloan.utilities.AppUtils;

import ss.com.bannerslider.ImageLoadingService;

public class BannerImageLoadingService implements ImageLoadingService {
    public Context context;

    public BannerImageLoadingService(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        AppUtils.loadImage(context, url, imageView);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        AppUtils.loadImage(context, resource, imageView);

    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        // AppUtils.loadImageWithoutCache(context, url, imageView, placeHolder);

    }
}