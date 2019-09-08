package com.hack.easyhomeloan.activities.login;


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.activities.home.HomeActivity;
import com.hack.easyhomeloan.activities.home.banner.BannerSliderAdapter;
import com.hack.easyhomeloan.base.BaseActivity;
import com.hack.easyhomeloan.base.BaseResponse;
import com.hack.easyhomeloan.databinding.ActivityLoginBinding;
import com.hack.easyhomeloan.dialog.CustomDialog;
import com.hack.easyhomeloan.utilities.AppConstant;
import com.hack.easyhomeloan.utilities.AppUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * Login Activity
 */
public class LoginActivity extends BaseActivity {

    private LoginViewModel loginVM;
    private ActivityLoginBinding mBinding;


    @Override
    protected int getActivityLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityLoginBinding) viewDataBinding;
        loginVM = ViewModelProviders.of(this).get(LoginViewModel.class);
        mBinding.setLoginViewModel(loginVM);
        observeClicks();
        observeApiResponse();
        setUpBannerData();
    }

    private void observeClicks() {
        setSmoothScroll();
        loginVM.viewClick.observe(this, (Observer<Integer>) id -> {
            if (id == R.id.btnLogin) {
                if (isValidateData()) {
                    loginVM.doCheckEligibility(loginVM.userId.toString());
                }
            }
        });


    }

    @SuppressLint("ClickableViewAccessibility")
    private void setSmoothScroll() {
        mBinding.etUserName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ObjectAnimator animator = ObjectAnimator.ofInt(mBinding.scrollView, "scrollY", 250);
                animator.setDuration(750);
                animator.start();
                return false;
            }
        });
    }

    private void observeApiResponse() {
        loginVM.apiResponse.observe(this, new Observer() {
            @Override
            public void onChanged(Object response) {
                if (response instanceof BaseResponse) {
                    if (((BaseResponse) response).status.getStatusCode() == AppConstant.SUCCESS_STATUS_CODE) {
                        navigateToDashboard();
                    } else {
                        new CustomDialog.Builder(LoginActivity.this)
                                .setDialogTitle("Heads Up!")
                                .setMessageText(((BaseResponse) response).status.getStatusMessage())
                                .build();
                    }

                    loginVM.apiResponse.setValue(null);
                } else if (response instanceof Throwable) {
                    AppUtils.showToastMessage(LoginActivity.this, getString(R.string.error_msg));
                    loginVM.apiResponse.setValue(null);
                }
            }
        });

    }

    private void setUpBannerData() {
        String[] images = {"https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg",
                "https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg",
                "https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg"};
        mBinding.bottomCarousal.setAdapter(new BannerSliderAdapter(Arrays.asList(images)));
    }

    private void navigateToDashboard() {
        Bundle bundleArgs = new Bundle();
        bundleArgs.putString(HomeActivity.KEY_USER_ID, Objects.requireNonNull(loginVM.userId.get()).toString());
        AppUtils.navigateToActivity(
                this,
                HomeActivity.class,
                bundleArgs,
                true
        );
    }


    /**
     * @return
     */
    private boolean isValidateData() {
        if (loginVM.userId.get() == null || TextUtils.isEmpty(Objects.requireNonNull(loginVM.userId.get()).toString())) {
            loginVM.errorText.set(getString(R.string.empty_user_id));
            return false;
        } else if (loginVM.userId.get().toString().length() < 10) {
            loginVM.errorText.set(getString(R.string.userId_length_invalid));
            return false;
        } else {
            loginVM.errorText.set(null);
        }
        return true;
    }


}
