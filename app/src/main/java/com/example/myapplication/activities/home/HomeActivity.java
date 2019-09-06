package com.example.myapplication.activities.home;


import android.Manifest;
import android.view.Menu;

import androidx.core.app.ActivityCompat;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.Hero;
import com.example.myapplication.R;
import com.example.myapplication.activities.home.adapter.HeroesAdapter;
import com.example.myapplication.activities.home.viewmodel.HomeViewModel;
import com.example.myapplication.base.navigation.BaseNavigationDrawerActivity;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.dialog.LoadingDialog;
import com.example.myapplication.utilities.AppConstant;
import com.example.myapplication.utilities.AppUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeActivity extends BaseNavigationDrawerActivity {
    public static final String TAG = HomeActivity.class.getSimpleName();
    private HomeViewModel mViewModel;
    private ActivityMainBinding binding;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        menu.findItem(R.id.notification).setVisible(true).setOnMenuItemClickListener(menuItem -> {
            //Navigator.launchActivity(HomeActivity.this, IntentHelper.getInstance().newNotificationIntent(HomeActivity.this));
            return true;
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        binding = (ActivityMainBinding) viewDataBinding;
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        observeClicks();
        observeApiResponses();

        //api callling
        LoadingDialog.show(this);
        mViewModel.getCityList();
    }


    @Override
    protected void onActivityReady() {
        super.onActivityReady();
        checkPermission();
        // setupToolbar();

    }


    private void setupToolbar() {
      /*  IncludeToolbarBinding toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbarActionbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbarBinding.toolbarActionbar.setNavigationOnClickListener(v -> onBackPressed());
        }*/
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
            LoadingDialog.dismissDialog();
            if (response instanceof Throwable) {
                //handle error
                mViewModel.apiResponse.setValue(null);
            } else if (response instanceof List) {
                AppUtils.setRecyclerBasicProperties(this, binding.rvHomeList);
                HeroesAdapter adapter = new HeroesAdapter(this, (List<Hero>) response);
                binding.rvHomeList.setAdapter(adapter);
                mViewModel.apiResponse.setValue(null);
            }

        });
    }

    private void observeClicks() {
        mViewModel.viewClick.observe(this, (Observer<Integer>) id -> {
            if (id == binding.rvHomeList.getId()) {
                mViewModel.viewClick.setValue(null);
            }


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
