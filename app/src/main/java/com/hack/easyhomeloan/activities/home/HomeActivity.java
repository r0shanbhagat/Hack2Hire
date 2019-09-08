package com.hack.easyhomeloan.activities.home;


import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.activities.home.banner.BannerSliderAdapter;
import com.hack.easyhomeloan.activities.home.communication.BannerData;
import com.hack.easyhomeloan.activities.home.communication.HomeDataResponse;
import com.hack.easyhomeloan.activities.home.dialog.FullScreenBannerDialog;
import com.hack.easyhomeloan.activities.home.viewmodel.HomeViewModel;
import com.hack.easyhomeloan.base.BaseDialog;
import com.hack.easyhomeloan.base.BaseResponse;
import com.hack.easyhomeloan.base.navigation.BaseNavigationDrawerActivity;
import com.hack.easyhomeloan.base.navigation.menubean.NavigationMenuModel;
import com.hack.easyhomeloan.databinding.ActivityHomeBinding;
import com.hack.easyhomeloan.dialog.CustomDialog;
import com.hack.easyhomeloan.utilities.AppConstant;
import com.hack.easyhomeloan.utilities.AppUtils;
import com.hack.easyhomeloan.view.SkeletonScreenView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseNavigationDrawerActivity {
    public static final String TAG = HomeActivity.class.getSimpleName();
    public static final String KEY_USER_ID = "key_user_id";
    private HomeViewModel mViewModel;
    private ActivityHomeBinding binding;
    private String userId;
    private HomeDataResponse dashboardData;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        binding = (ActivityHomeBinding) viewDataBinding;
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        observeClicks();
        observeApiResponses();
        SkeletonScreenView.show(binding.mainLayout, R.layout.activity_home_skeleton); //uncomment
        intiArgs();
        mViewModel.getHomeDashboardData(userId);
        showFullBannerDialog();
    }

    private void intiArgs() {
        Bundle bundleArgs = getIntent().getExtras();
        if (null != bundleArgs) {
            userId = bundleArgs.getString(KEY_USER_ID);
        }
    }


    @Override
    protected void onActivityReady() {
        super.onActivityReady();
        checkPermission();
    }


    private void showFullBannerDialog() {
        if (/*AppUtils.isListNotEmpty(dashboardData.getRecommendationBanner())||*/ true) {
            List<BannerData> bannerDataList = new ArrayList<>();
            BannerData bd = new BannerData();
            bd.setCategoryId("1");
            bd.setImageUrl("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg");
            bannerDataList.add(bd);
            BannerData bd2 = new BannerData();
            bd2.setCategoryId("2");
            bd2.setImageUrl("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg");
            bannerDataList.add(bd2);

            BannerData bd3 = new BannerData();
            bd3.setCategoryId("1");
            bd3.setImageUrl("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg");
            bannerDataList.add(bd3);
            FullScreenBannerDialog fullScreenBannerDialog = FullScreenBannerDialog.showDialog(this,
                    getSupportFragmentManager(), bannerDataList /*dashboardData.getRecommendationBanner()*/);
            fullScreenBannerDialog.setOnItemClickListener(new BaseDialog.IDialogClick<NavigationMenuModel>() {
                @Override
                public void dialogMessageCallback(View view, NavigationMenuModel notificationItemModel, int requestCode) {

                }
            });
        }
    }


    private void checkPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                },
                AppConstant.BASIC_PERMISSION);
    }

    private void observeApiResponses() {
        mViewModel.apiResponse.observe(this, response -> {
            if (response instanceof Throwable) {
                AppUtils.showToastMessage(this, getString(R.string.error_msg));
                mViewModel.apiResponse.setValue(null);
            } else if (response instanceof HomeDataResponse) {
                if (((HomeDataResponse) response).getStatus().getStatusCode() == AppConstant.SUCCESS_STATUS_CODE) {
                    //show Banner,DEsc,popUp
                    dashboardData = (HomeDataResponse) response;
                    setupDataBoardView();
                } else {
                    if (null != ((BaseResponse) response).status.getStatusMessage()) {
                        new CustomDialog.Builder(HomeActivity.this)
                                .setDialogTitle("Heads Up!")
                                .setCancelText("OK")
                                .setMessageText(((BaseResponse) response).status.getStatusMessage())
                                .build().show();
                    }
                }
                mViewModel.apiResponse.setValue(null);
            }

        });
    }

    private void setupDataBoardView() {
        setUpBannerData();
        setUpUserData();
        showFullBannerDialog();
    }

    private void setUpBannerData() {
        if (AppUtils.isListNotEmpty(dashboardData.getTopBanner())) {
            binding.bannerCarousal.setAdapter(new BannerSliderAdapter(dashboardData.getTopBanner()));
            binding.bannerCarousal.setInterval(5000);
        }
    }


    private void setUpUserData() {
        if (null != dashboardData.getUserInformation()) {
            mViewModel.userId.set(dashboardData.getUserInformation().getCustomerId());
            mViewModel.name.set(dashboardData.getUserInformation().getName());
            mViewModel.gender.set(dashboardData.getUserInformation().getGender());
            mViewModel.address.set(dashboardData.getUserInformation().getAddress());
        }
    }

    private void observeClicks() {
        mViewModel.viewClick.observe(this, (Observer<Integer>) id -> {
           /* if (id == binding.rvHomeList.getId()) {
                mViewModel.viewClick.setValue(null);
            }
*/
        });
    }


    @Override
    public void onBackPressed() {
        if (!isClosedNavDrawer()) {
            if (!flag_temp) {
                Snackbar snackbar = Snackbar.make(mDrawerLayout, "Press Back again to exit", Snackbar.LENGTH_SHORT);
                snackbar.show();
                flag_temp = true;
            } else if (back_pressed + 2000 > System.currentTimeMillis() && flag_temp) {
                super.onBackPressed();
                return;
            } else {
                flag_temp = false;
            }
            back_pressed = System.currentTimeMillis();
        }
    }

}
