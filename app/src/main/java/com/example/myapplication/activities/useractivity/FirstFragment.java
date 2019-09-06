package com.example.myapplication.activities.useractivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.activities.useractivity.login.LoginViewModel;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.base.BaseViewModel;
import com.example.myapplication.databinding.NavigationFirstFragmentBinding;
import com.example.myapplication.utilities.AppUtils;

import java.util.Objects;

public class FirstFragment extends BaseFragment<NavigationFirstFragmentBinding, BaseViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.navigation_first_fragment;
    }

    @Override
    public LoginViewModel getViewModel() {
        return  ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.loginViewModel;
    }

    @Override
    protected void onViewCreated(NavigationFirstFragmentBinding viewDataBinding) {
        final Bundle bundle = new Bundle();
        bundle.putBoolean("test_boolean", true);

        viewDataBinding.buttonFrag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavOptions.Builder navOptionBuilder = new NavOptions.Builder();
                 AppUtils.applyAnimation(navOptionBuilder);
                //   AppUtils.applyReverseAnimation(navOptionBuilder);
                ((UserActivity) Objects.requireNonNull(getActivity()))
                        .navController
                        .navigate(R.id.secondFragment, bundle,navOptionBuilder.build());
            }
        });
    }

}
