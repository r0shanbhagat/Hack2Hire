package com.hack.easyhomeloan.activities.home.dialog;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.load.engine.GlideException;
import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.activities.home.communication.BannerData;
import com.hack.easyhomeloan.activities.listing.ListingActivity;
import com.hack.easyhomeloan.base.BaseActivity;
import com.hack.easyhomeloan.base.BaseDialog;
import com.hack.easyhomeloan.base.navigation.menubean.NavigationMenuModel;
import com.hack.easyhomeloan.databinding.DialogFullScreenBinding;
import com.hack.easyhomeloan.utilities.AppUtils;

import org.parceler.Parcels;

import java.util.List;

public class FullScreenBannerDialog extends AbstractBaseFullScreenDialog {

    public static final String KEY_URL = "DIALOG_KEY_URL";
    public static String TAG = FullScreenBannerDialog.class.getSimpleName();
    private List<BannerData> recommendedBannerData;
    private DialogFullScreenBinding binding;

    private BaseDialog.IDialogClick<NavigationMenuModel> clickListener;

    public static FullScreenBannerDialog showDialog(final Context context, final FragmentManager fragmentTransaction,
                                                    final List<BannerData> recommendedBannerData) {
        final FullScreenBannerDialog fragment = new FullScreenBannerDialog();
        BaseActivity.getBaseActivity().runOnUiThread(() ->
                AppUtils.loadImage(context, recommendedBannerData.get(0).getImageUrl(),
                        new ImageLoadCallBack() {
                            @Override
                            public void onSuccess(Object resource) {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(KEY_URL, Parcels.wrap(recommendedBannerData));
                                fragment.setArguments(bundle);
                                FragmentTransaction ft = fragmentTransaction.beginTransaction();
                                ft.add(fragment, TAG);
                                ft.commitAllowingStateLoss();
                            }

                            @Override
                            public void onFailure(GlideException ge) {
                                AppUtils.showException(ge);
                            }
                        }));

        return fragment;
    }


    public void setOnItemClickListener(BaseDialog.IDialogClick<NavigationMenuModel> clickListener) {
        this.clickListener = clickListener;
    }

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
