package com.example.myapplication.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.utilities.AppUtils;
import com.example.myapplication.utilities.HideUtility;
import com.example.myapplication.view.FragmentAnimation;


public abstract class BaseActivity extends AppCompatActivity {

    private static BaseActivity baseActivity;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static BaseActivity getBaseActivity() {
        return baseActivity;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        SavedInstanceFragment.getInstance(getFragmentManager()).pushData((Bundle) outState.clone());
        outState.clear(); // We don't want a TransactionTooLargeException, so we handle things via the SavedInstanceFragment
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        try {
            super.onRestoreInstanceState(SavedInstanceFragment.getInstance(getFragmentManager()).popData());
        } catch (Exception e) {
            AppUtils.showException(e);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(SavedInstanceFragment.getInstance(getFragmentManager()).popData());
        baseActivity = this;
        onPreSetContentView();
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, getActivityLayout());
        HideUtility.init(this);
        onCreate(viewDataBinding);
        onActivityReady();
    }


    protected void onPreSetContentView() {
        //To minimize the overdraw of the screen
        getWindow().setBackgroundDrawable(null);
    }

    public void hideKeyboard() {
        HideUtility.hideSoftInput(this);
    }

    protected void onActivityReady() {
        // do nothing
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment baseFragment = AppUtils.getActiveFragment(getBaseActivity());
        if (null != baseFragment) {
            baseFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]  permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Fragment baseFragment = AppUtils.getActiveFragment(getBaseActivity());
        if (null != baseFragment) {
            baseFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isChangingConfigurations()) {
            attachBaseContext(getBaseContext());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    protected abstract int getActivityLayout();

    protected abstract void onCreate(ViewDataBinding viewDataBinding);

    public void pushFragment(FragmentAnimation animation, Bundle bundle, Fragment destinationFragment, int containerId, String tag,
                             boolean addToBackstack) {
        if (destinationFragment == null) {
            throw new IllegalArgumentException("Destination Fragment is not defined.");
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (animation != null) {
            ft.setCustomAnimations(animation.getEnter(), animation.getExit(), animation.getPopEnter(), animation.getPopExit());
        }
        if (bundle != null) {
            destinationFragment.setArguments(bundle);
        }

        if (destinationFragment.isAdded()) {
            ft.show(destinationFragment);
        } else {
            ft.replace(containerId, destinationFragment, tag);
        }
        if (addToBackstack) {
            ft.addToBackStack(tag);
        }
        if (!isFinishing()) {
            ft.commitAllowingStateLoss();
        }
    }
}
