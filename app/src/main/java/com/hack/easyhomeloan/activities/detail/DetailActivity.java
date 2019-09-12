package com.hack.easyhomeloan.activities.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.activities.detail.model.EmiValues;
import com.hack.easyhomeloan.activities.detail.viewmodel.DetailViewModel;
import com.hack.easyhomeloan.activities.home.HomeActivity;
import com.hack.easyhomeloan.activities.home.viewmodel.HomeViewModel;
import com.hack.easyhomeloan.base.BaseActivity;
import com.hack.easyhomeloan.databinding.ActivityDetailBinding;
import com.hack.easyhomeloan.databinding.ActivityHomeBinding;
import com.hack.easyhomeloan.utilities.AppUtils;
import com.hack.easyhomeloan.utilities.FinanceUtils;
import com.hack.easyhomeloan.utilities.IAsyncCallBack;

import java.util.Objects;

public class DetailActivity extends BaseActivity {

    private ActivityDetailBinding binding;
    private DetailViewModel mViewModel;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        binding = (ActivityDetailBinding) viewDataBinding;
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        binding.setDetailViewModel(mViewModel);
        observeClicks();
    }

    private void observeClicks() {
        mViewModel.viewClick.observe(this, (Observer<Integer>) id -> {
            if (id == R.id.button) {
                calculateEmi();
                //mViewModel.viewClick.setValue(null);
            } else if (id == R.id.btnAmortization) {
                //mViewModel.viewClick.setValue(null);
            } else if (id == R.id.btnGraphView) {
                //mViewModel.viewClick.setValue(null);
            }
        });
    }

    private void calculateEmi() {
        try {
            int tenure = Integer.parseInt(binding.edtTenure.getText().toString());
            double roi = Double.parseDouble(binding.edtRoi.getText().toString());
            double loanAmount = Double.parseDouble(binding.edtLoanAmount.getText().toString());
            binding.cvAmort.setVisibility(View.VISIBLE);
            binding.button.setText(getResources().getString(R.string.calculate));
            double emi = FinanceUtils.calculateEMI(tenure,
                    roi, loanAmount, false);
            binding.tvEMI.setText(getString(R.string.rupeeSymbol) + Math.ceil(emi));
            //  model.setCalculateEmi(getRupeeSign() + NormalUtility.getCommaSeparatedValue(MathUtility.getCeilValue(emi)));
        } catch (Exception e) {

        }

    }


}
