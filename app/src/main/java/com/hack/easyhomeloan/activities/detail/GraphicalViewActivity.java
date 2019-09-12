package com.hack.easyhomeloan.activities.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MenuItem;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.ViewDataBinding;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.activities.detail.model.EmiValues;
import com.hack.easyhomeloan.base.BaseActivity;
import com.hack.easyhomeloan.databinding.ActivityGraphicalViewBinding;
import com.hack.easyhomeloan.utilities.AppUtils;
import com.hack.easyhomeloan.view.EmiCircleDisplay;

import java.text.DecimalFormat;
import java.util.List;


public class GraphicalViewActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = GraphicalViewActivity.class.getSimpleName();
    private static final String TITLE = "Graphical Pie Chart";
    private ActivityGraphicalViewBinding binding;
    private EmiValues emiValues;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_graphical_view;
    }


    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        binding = (ActivityGraphicalViewBinding) viewDataBinding;
        emiValues = getIntent().getParcelableExtra("EmiValues");
        if (null != emiValues) {
            calculatenShow(emiValues);
        }

        binding.btnAmort.setOnClickListener(this);
    }

    private void calculatenShow(EmiValues emiValues) {
        double totalInterest = 0;
        double totalPrincipal = 0;
        double totalOutStanding;

        List<String> interestLs = emiValues.getRowIntrest();
        for (String interest : interestLs) {
            try {
                double value = Double.parseDouble(interest);
                totalInterest += value;
            } catch (Exception e) {
                AppUtils.showException(TAG, e);
            }
        }

        List<String> principalLs = emiValues.getRowPrincipal();
        for (String principal : principalLs) {
            try {
                double value = Double.parseDouble(principal);
                totalPrincipal += value;
            } catch (Exception e) {
                AppUtils.showException(TAG, e);
            }
        }
        totalOutStanding = totalInterest + totalPrincipal;

        DecimalFormat decimalFormatTwoDigit = new DecimalFormat("##,##,###");
        binding.tvInterest.setText(decimalFormatTwoDigit.format(totalInterest));
        binding.tvPrincipal.setText(decimalFormatTwoDigit.format(totalPrincipal));
        binding.tvTotalAmount.setText(decimalFormatTwoDigit.format(totalOutStanding));
        if (totalInterest > totalOutStanding) {
            totalInterest = totalOutStanding;
        }
        setCircleView(binding.ecdInterestPayable, totalOutStanding, totalInterest, this, "%", ResourcesCompat.getColor(getResources(), R.color.colorRed, null));
        if (totalPrincipal > totalOutStanding) {
            totalPrincipal = totalOutStanding;
        }
        setCircleView(binding.ecdPrincipal, totalOutStanding, totalPrincipal, this, "%", ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
    }

    private void setCircleView(EmiCircleDisplay mCircleDisplay, double totalCount, double dataCount, Context context, String unitText, int color) {
        double total, current;
        total = totalCount;
        current = dataCount;
        mCircleDisplay.setAnimDuration(2000);
        mCircleDisplay.setValueWidthPercent(15f);
        mCircleDisplay.setTouchEnabled(false);
        mCircleDisplay.setStepSize(0.5f);
        mCircleDisplay.setSecondaryColor(Color.parseColor("#DDDDDD"));
        mCircleDisplay.showValuePercentage((float) current, (float) total, true);
        mCircleDisplay.setColor(color);
        mCircleDisplay.setUnit(unitText);
        mCircleDisplay.setTextSize(12f);
        mCircleDisplay.setInnerCircleColor(context.getResources().getColor(R.color.colorWhite));
        mCircleDisplay.setTextColor(context.getResources().getColor(R.color.colorBlack));
        mCircleDisplay.mSecondTextPaint.setColor(context.getResources().getColor(R.color.colorBlack));
    }

    @Override
    public void onBackPressed() {
        super.finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.finish();
            return true;
        }
        return false;
    }


    @Override
    protected void onActivityReady() {
        super.onActivityReady();
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(TITLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAmort) {
            Intent intent = new Intent(this, AmortizationActivity.class);
            intent.putExtra("EmiValues", emiValues);
            startActivity(intent);
            finish();
        }
    }


}
