package com.example.myapplication.activities.useractivity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.databinding.ActivityUserBinding;
import com.example.myapplication.databinding.IncludeToolbarBinding;

public class UserActivity extends BaseActivity {
    public NavController navController;
    private ActivityUserBinding binding;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_user;
    }

    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        binding = (ActivityUserBinding) viewDataBinding;
        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
        navController.addOnDestinationChangedListener((controller, destination, arguments) ->
                Log.d("TAG", destination.getLabel() + " "));
    }


    @Override
    protected void onActivityReady() {
        super.onActivityReady();
        setupToolbar();
    }

    private void setupToolbar() {
        IncludeToolbarBinding toolbarBinding = binding.toolbarSignUp;
        setSupportActionBar(toolbarBinding.toolbarActionbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbarBinding.toolbarActionbar.setNavigationOnClickListener(v -> onBackPressed());
        }
    }
}