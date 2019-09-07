package com.hack.easyhomeloan.activities.home.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.activities.home.communication.BannerData;
import com.hack.easyhomeloan.base.BaseDialog;

import java.util.List;

public class RecommendationPagerAdapter extends PagerAdapter {
    private Context context;
    private List<BannerData> bannerDataList;
    private LayoutInflater layoutInflater;
    private BaseDialog.IDialogClick iDialogClick;


    public RecommendationPagerAdapter(Context context, List<BannerData> bannerDataList, BaseDialog.IDialogClick<String> iDialogClick) {
        this.context = context;
        this.bannerDataList = bannerDataList;
        this.iDialogClick = iDialogClick;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bannerDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.recommendation_item, container, false);
        BannerData bannerData = bannerDataList.get(position);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        TextView tvPropertyName = itemView.findViewById(R.id.tvPropertyName);
        tvPropertyName.setText(bannerData.getCategoryName());
        container.addView(itemView);
        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDialogClick.dialogMessageCallback(v, bannerData.getCategoryId(), 0);
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


}