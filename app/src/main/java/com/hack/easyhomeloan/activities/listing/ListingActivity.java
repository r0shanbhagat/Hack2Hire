package com.hack.easyhomeloan.activities.listing;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.activities.listing.adapter.HomeListAdapter;
import com.hack.easyhomeloan.activities.listing.model.HomeItem;
import com.hack.easyhomeloan.base.BaseActivity;

import java.util.ArrayList;

public class ListingActivity extends BaseActivity implements HomeListAdapter.ProductAdapterStatus {
    public static final String KEY_USER_ID = "key_user_id";
    String categoryId;
    private ArrayList<HomeItem> homeItems;
    private RelativeLayout backArrow;
    private RelativeLayout buyButton;
    private TextView flatList;
    private TextView homePrice;
    private TextView totalPrice;
    private TextView clear;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private HomeListAdapter homeListAdapter;
    private CardView payment_parent;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_list;
    }

    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        setType();
        intiArgs();
        setAdapterType();
        initProducts();
        clickBackArrow();
        clickPayment();
        setProductAdapter();
    }

    private void intiArgs() {
        Bundle bundleArgs = getIntent().getExtras();
        if (null != bundleArgs) {
            categoryId = bundleArgs.getString(KEY_USER_ID);
        }
    }


    private void setType() {
        backArrow = findViewById(R.id.back_arrow);
        flatList = findViewById(R.id.cart_list);
        buyButton = findViewById(R.id.payment_button);
        homePrice = findViewById(R.id.product_intrest);
        totalPrice = findViewById(R.id.total_price);
        clear = findViewById(R.id.empty_cart);
        payment_parent = findViewById(R.id.payment_parent);
    }

    private void setAdapterType() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setProductAdapter() {
        homeListAdapter = new HomeListAdapter(ListingActivity.this, homeItems, this);
        recyclerView.setAdapter(homeListAdapter);
    }

    private void setTotalPriceTitle(float total) {
        totalPrice.setText(getString(R.string.currency) + "" + total);
    }

    private void setShippingPriceTitle(float shipping) {
        homePrice.setText(getString(R.string.currency) + "" + shipping);
    }

    private void caliculatePrices() {
        float shipping = 0, total = 0;
        for (int i = 0; i < homeItems.size(); i++) {
            shipping += homeItems.get(i).getShippingPrice();
            total += homeItems.get(i).getProductPrice();
        }

        if (shipping != 0) {
            total += shipping;
        }
        setShippingPriceTitle(shipping);
        setTotalPriceTitle(total);
    }

    private void clickPayment() {
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (homeItems.size() != 0) {
                    Toast.makeText(ListingActivity.this, "Payment", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void clickBackArrow() {
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @SuppressLint("ResourceType")
    private void paymentButtonStyle() {
        if (homeItems.size() == 0) {
            buyButton.setClickable(false);
            buyButton.setFocusable(false);
            payment_parent.setCardBackgroundColor(Color.parseColor(getString(R.color.colorSubtitle)));
        } else {
            buyButton.setClickable(true);
            buyButton.setFocusable(true);
            payment_parent.setCardBackgroundColor(Color.parseColor(getString(R.color.colorPayment)));
        }
    }

    private void setHomeListCount(int piece) {
        if (piece == 0) {
            flatList.setText(getString(R.string.cart_list));
            clear.setVisibility(View.VISIBLE);
        } else {
            flatList.setText(getString(R.string.cart_list) + " ( " + piece + " )");
            clear.setVisibility(View.GONE);
        }
    }

    private void initProducts() {
        homeItems = new ArrayList<>();
        homeItems.add(new HomeItem(11, "2 BHK", getString(R.string.item_1), R.drawable.flat_one, 4500000, 11));
        homeItems.add(new HomeItem(12, "3 BHK", getString(R.string.item_2), R.drawable.flat_2, 800000, 10));
        homeItems.add(new HomeItem(13, "3 BHK Villa", getString(R.string.item_3), R.drawable.flat_3, 9510000, 9.5f));
        homeItems.add(new HomeItem(14, "Independent Home", getString(R.string.item_4), R.drawable.flat_4, 650000, 11.2f));

        homeItems.add(new HomeItem(15, "3 BHK", getString(R.string.item_1), R.drawable.flat_one, 9510000, 6));
        homeItems.add(new HomeItem(16, "2 BHK", getString(R.string.item_2), R.drawable.flat_2, 650000, 7.7f));
        homeItems.add(new HomeItem(17, "1 BHK", getString(R.string.item_3), R.drawable.flat_3, 9510000, 8.2f));
        homeItems.add(new HomeItem(18, "Independent Home", getString(R.string.item_4), R.drawable.flat_4, 9100000, 8.15f));

        homeItems.add(new HomeItem(15, "Independent Home", getString(R.string.item_1), R.drawable.flat_one, 9510000, 9.5f));
        homeItems.add(new HomeItem(16, "3 BHK", getString(R.string.item_2), R.drawable.flat_2, 650000, 9.9f));
        homeItems.add(new HomeItem(17, "Villa", getString(R.string.item_3), R.drawable.flat_3, 1420000, 7));
        homeItems.add(new HomeItem(18, "2 BHK", getString(R.string.item_4), R.drawable.flat_4, 4500000, 15.6f));

        setHomeListCount(homeItems.size());
        caliculatePrices();
        paymentButtonStyle();
    }

    @Override
    public void DataRefresh(boolean isRefresh) {
        if (isRefresh) {
            setHomeListCount(homeItems.size());
            caliculatePrices();
            paymentButtonStyle();
        }
    }
}