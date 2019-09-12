package com.hack.easyhomeloan.activities.detail;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.activities.detail.adapter.AmortizationListAdapter;
import com.hack.easyhomeloan.activities.detail.model.EmiValues;
import com.hack.easyhomeloan.base.BaseActivity;
import com.hack.easyhomeloan.databinding.ActivityAmortizationBinding;


public class AmortizationActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = AmortizationActivity.class.getSimpleName();
    private static final String TITLE = "Amortization Table";
    private ActivityAmortizationBinding binding;
    private EmiValues emiValues;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_amortization;
    }


    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        binding = (ActivityAmortizationBinding) viewDataBinding;

        emiValues = getIntent().getParcelableExtra("EmiValues");

        binding.list.setAdapter(new AmortizationListAdapter(this, emiValues.getRowMonth(),
                emiValues.getRowEMI(), emiValues.getRowPrincipal(), emiValues.getRowOutStanding(), emiValues.getRowIntrest()));

        binding.btnGraph.setOnClickListener(this);
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
        if (view.getId() == R.id.btnGraph) {
            Intent intent = new Intent(this, GraphicalViewActivity.class);
            intent.putExtra("EmiValues", emiValues);
            startActivity(intent);
            finish();
        }
    }


}
