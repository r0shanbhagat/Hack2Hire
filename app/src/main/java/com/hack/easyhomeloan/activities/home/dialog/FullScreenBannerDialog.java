package com.hack.easyhomeloan.activities.home.dialog;


import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.activities.home.communication.BannerData;
import com.hack.easyhomeloan.activities.listing.ListingActivity;
import com.hack.easyhomeloan.base.BaseActivity;
import com.hack.easyhomeloan.base.BaseDialog;
import com.hack.easyhomeloan.databinding.DialogFullScreenBinding;
import com.hack.easyhomeloan.utilities.AppUtils;

import org.parceler.Parcels;

import java.util.List;

public class FullScreenBannerDialog extends AbstractBaseFullScreenDialog {

    public static final String KEY_URL = "DIALOG_KEY_URL";
    public static String TAG = FullScreenBannerDialog.class.getSimpleName();
    private List<BannerData> recommendedBannerData;
    private DialogFullScreenBinding binding;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_full_screen;
    }

    @Override
    protected void create(ViewDataBinding viewDataBinding) {
        binding = (DialogFullScreenBinding) viewDataBinding;
        binding.setBannerDialog(this);
        if (null != getArguments()) {
            recommendedBannerData = Parcels.unwrap(getArguments().getParcelable(KEY_URL));
            binding.pager.setAdapter(new RecommendationPagerAdapter(getContext(), recommendedBannerData, new BaseDialog.IDialogClick<String>() {
                @Override
                public void dialogMessageCallback(View view, String objectText, int requestCode) {
                    Bundle bundleArgs = new Bundle();
                    bundleArgs.putString(ListingActivity.KEY_USER_ID, objectText);
                    AppUtils.navigateToActivity(
                            BaseActivity.getBaseActivity(),
                            ListingActivity.class,
                            bundleArgs,
                            false
                    );
                }
            }));
            binding.indicator.setViewPager(binding.pager);
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llClose: {
                dismiss();
                break;
            }
            case R.id.left_nav: {
                binding.pager.setCurrentItem(getPagerIndex(false));
                break;
            }
            case R.id.right_nav: {
                binding.pager.setCurrentItem(getPagerIndex(true));
                break;
            }
        }
    }

    int getPagerIndex(boolean isRightNav) {
        int currentIndex = binding.pager.getCurrentItem();
        int index = 0;
        if (isRightNav) {
            if (currentIndex == binding.pager.getAdapter().getCount()) {
                index = 0;
            } else {
                index = currentIndex + 1;
            }

        } else {
            if (currentIndex != 0) {
                index = currentIndex - 1;
            }
        }
        return index;
    }


}
