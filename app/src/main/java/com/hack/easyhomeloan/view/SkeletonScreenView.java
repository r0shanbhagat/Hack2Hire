package com.hack.easyhomeloan.view;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.hack.easyhomeloan.R;


/**
 * SkeletonScreenView Class :
 *
 * @author Roshan Kumar
 * @version 3.0
 * @since 9/1/19
 */
public class SkeletonScreenView {
    private static SkeletonScreen skeletonScreen;

    public static void show(View bindableView, int skeletonLayoutResID) {
        skeletonScreen = Skeleton.bind(bindableView)
                .load(skeletonLayoutResID)
                .duration(1000)
                .angle(0)
                .color(R.color.shimmer_color)
                .show();
    }

    public static void show(RecyclerView recyclerView, RecyclerView.Adapter adapter, int skeletonLayoutResID) {
        skeletonScreen = Skeleton.bind(recyclerView)
                .adapter(adapter)
                .angle(0)
                .shimmer(true)
                .duration(1000)
                .count(10)
                .color(R.color.dark_transparent)
                .load(skeletonLayoutResID)
                .frozen(false)
                .show();
    }

    public static void hide() {
        if (null != skeletonScreen) {
            skeletonScreen.hide();
        }
    }
}
