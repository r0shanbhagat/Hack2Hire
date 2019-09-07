package com.hack.easyhomeloan.activities.home.banner;


import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class BannerSliderAdapter extends SliderAdapter {

    private List<String> bannerImageList;

    public BannerSliderAdapter(List<String> bannerImageList) {
        this.bannerImageList = bannerImageList;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        viewHolder.bindImageSlide(bannerImageList.get(position));
    }

    @Override
    public int getItemCount() {
        return null != bannerImageList ? bannerImageList.size() : 0;
    }
}