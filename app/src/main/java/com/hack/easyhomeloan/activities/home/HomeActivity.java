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
import com.hack.easyhomeloan.activities.home.communication.UserInformation;
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
        binding.setHomeViewModel(mViewModel);
        observeClicks();
        observeApiResponses();
        SkeletonScreenView.show(binding.mainLayout, R.layout.activity_home_skeleton); //uncomment
        intiArgs();
        mViewModel.getHomeDashboardData(userId);
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
        if (AppUtils.isListNotEmpty(dashboardData.getRecommendationBanner())) {
            FullScreenBannerDialog fullScreenBannerDialog = FullScreenBannerDialog.showDialog(this,
                    getSupportFragmentManager(), dashboardData.getRecommendationBanner());
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
                // AppUtils.showToastMessage(this, getString(R.string.error_msg));
                SkeletonScreenView.hide();
                dashboardData = getMockData();
                setupDataBoardView();
                //    mViewModel.apiResponse.setValue(null);
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
                //  mViewModel.apiResponse.setValue(null);
            }

        });
    }


    private HomeDataResponse getMockData() {
        HomeDataResponse homeDataResponse = new HomeDataResponse();
        List<String> topBannerList = new ArrayList<>();
        topBannerList.add("https://www.buyrentkenya.com/uploadedfiles/ed/5d/ae/ed5daec1-c0fc-4959-9640-97fac7c7f274.jpg");
        topBannerList.add("https://jainhousing.com/wp-content/uploads/2018/03/plam-medows-1024-non-offer.jpg");
        topBannerList.add("https://www.bankbazaar.com/images/india/infographic/Mobile-CT-banner-finance-mobile-1-img.gif");
        topBannerList.add("https://www.advertgallery.com/wp-content/uploads/2017/10/golden-nest-festival-offer-2bhk-flats-starting-from-rs-40-lac-ad-pune-times-06-10-2017.jpg");
        topBannerList.add("https://i.ytimg.com/vi/etvTly-VVH8/maxresdefault.jpg");
        topBannerList.add("https://hungamadeal.co.in/wp-content/uploads/2018/06/other-1.png");

        UserInformation userInformation = new UserInformation();
        userInformation.setName("Prem Rama Kathupally");
        userInformation.setAddress("TNGOS,Telangana Colony,Gachibowli");
        userInformation.setGender("Male");
        userInformation.setCustomerId(userId);

        List<BannerData> bannerDataList = new ArrayList<>();
        BannerData bannerData = new BannerData();
        bannerData.setCategoryId("1");
        bannerData.setCategoryName("2 BHK Flat");
        bannerData.setCategoryValue(getString(R.string.rupeeSymbol) + " 7,000,000");
        bannerData.setRegion("Madhopur");
        bannerDataList.add(bannerData);

        BannerData bannerData2 = new BannerData();
        bannerData2.setCategoryId("2");
        bannerData2.setCategoryName("Independent House");
        bannerData2.setCategoryValue(getString(R.string.rupeeSymbol) + " 8,500,000");
        bannerData2.setRegion("Secunderabad");
        bannerDataList.add(bannerData2);

        BannerData bannerData3 = new BannerData();
        bannerData3.setCategoryId("3");
        bannerData3.setCategoryName("Villa");
        bannerData3.setCategoryValue(getString(R.string.rupeeSymbol) + " 10,000,000");
        bannerData3.setRegion("Botanical Garden");
        bannerDataList.add(bannerData3);

        BannerData bannerData4 = new BannerData();
        bannerData4.setCategoryId("4");
        bannerData4.setCategoryName("1 BHK Flat");
        bannerData4.setCategoryValue(getString(R.string.rupeeSymbol) + " 50,000,00");
        bannerData4.setRegion("Mipur");
        bannerDataList.add(bannerData4);

        homeDataResponse.setTopBanner(topBannerList);
        homeDataResponse.setUserInformation(userInformation);
        homeDataResponse.setRecommendationBanner(bannerDataList);
        return homeDataResponse;
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
